/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.awolf.tags.Property;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class pool_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   46 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   49 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   50 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   51 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   58 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   63 */     ArrayList list = null;
/*   64 */     StringBuffer sbf = new StringBuffer();
/*   65 */     ManagedApplication mo = new ManagedApplication();
/*   66 */     if (distinct)
/*      */     {
/*   68 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   72 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   75 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   77 */       ArrayList row = (ArrayList)list.get(i);
/*   78 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   79 */       if (distinct) {
/*   80 */         sbf.append(row.get(0));
/*      */       } else
/*   82 */         sbf.append(row.get(1));
/*   83 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   86 */     return sbf.toString(); }
/*      */   
/*   88 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   91 */     if (severity == null)
/*      */     {
/*   93 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   95 */     if (severity.equals("5"))
/*      */     {
/*   97 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   99 */     if (severity.equals("1"))
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  106 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  113 */     if (severity == null)
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  117 */     if (severity.equals("1"))
/*      */     {
/*  119 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  121 */     if (severity.equals("4"))
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("5"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  132 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  138 */     if (severity == null)
/*      */     {
/*  140 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  142 */     if (severity.equals("5"))
/*      */     {
/*  144 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  146 */     if (severity.equals("1"))
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  152 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  158 */     if (severity == null)
/*      */     {
/*  160 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  162 */     if (severity.equals("1"))
/*      */     {
/*  164 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  166 */     if (severity.equals("4"))
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  170 */     if (severity.equals("5"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  176 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  182 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  188 */     if (severity == 5)
/*      */     {
/*  190 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  192 */     if (severity == 1)
/*      */     {
/*  194 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  199 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  205 */     if (severity == null)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  209 */     if (severity.equals("5"))
/*      */     {
/*  211 */       if (isAvailability) {
/*  212 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  215 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  218 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  220 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  222 */     if (severity.equals("1"))
/*      */     {
/*  224 */       if (isAvailability) {
/*  225 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  228 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  235 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  242 */     if (severity == null)
/*      */     {
/*  244 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  246 */     if (severity.equals("5"))
/*      */     {
/*  248 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  250 */     if (severity.equals("4"))
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("1"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  261 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  267 */     if (severity == null)
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  271 */     if (severity.equals("5"))
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("4"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("1"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  286 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  293 */     if (severity == null)
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  297 */     if (severity.equals("5"))
/*      */     {
/*  299 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  301 */     if (severity.equals("4"))
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  305 */     if (severity.equals("1"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  312 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  320 */     StringBuffer out = new StringBuffer();
/*  321 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  322 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  323 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  324 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  325 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  326 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  327 */     out.append("</tr>");
/*  328 */     out.append("</form></table>");
/*  329 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  336 */     if (val == null)
/*      */     {
/*  338 */       return "-";
/*      */     }
/*      */     
/*  341 */     String ret = FormatUtil.formatNumber(val);
/*  342 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  343 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  346 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  350 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  358 */     StringBuffer out = new StringBuffer();
/*  359 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  360 */     out.append("<tr>");
/*  361 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  363 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  365 */     out.append("</tr>");
/*  366 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  370 */       if (j % 2 == 0)
/*      */       {
/*  372 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  376 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  379 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  381 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  384 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  388 */       out.append("</tr>");
/*      */     }
/*  390 */     out.append("</table>");
/*  391 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  392 */     out.append("<tr>");
/*  393 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  394 */     out.append("</tr>");
/*  395 */     out.append("</table>");
/*  396 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  402 */     StringBuffer out = new StringBuffer();
/*  403 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  404 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  409 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  410 */     out.append("</tr>");
/*  411 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  414 */       out.append("<tr>");
/*  415 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  416 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  417 */       out.append("</tr>");
/*      */     }
/*      */     
/*  420 */     out.append("</table>");
/*  421 */     out.append("</table>");
/*  422 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  427 */     if (severity.equals("0"))
/*      */     {
/*  429 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  433 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  440 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  453 */     StringBuffer out = new StringBuffer();
/*  454 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  455 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  457 */       out.append("<tr>");
/*  458 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  459 */       out.append("</tr>");
/*      */       
/*      */ 
/*  462 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  464 */         String borderclass = "";
/*      */         
/*      */ 
/*  467 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  469 */         out.append("<tr>");
/*      */         
/*  471 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  472 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  473 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  479 */     out.append("</table><br>");
/*  480 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  481 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  483 */       List sLinks = secondLevelOfLinks[0];
/*  484 */       List sText = secondLevelOfLinks[1];
/*  485 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  488 */         out.append("<tr>");
/*  489 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  490 */         out.append("</tr>");
/*  491 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  493 */           String borderclass = "";
/*      */           
/*      */ 
/*  496 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  498 */           out.append("<tr>");
/*      */           
/*  500 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  501 */           if (sLinks.get(i).toString().length() == 0) {
/*  502 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  505 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  507 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  511 */     out.append("</table>");
/*  512 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  519 */     StringBuffer out = new StringBuffer();
/*  520 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  521 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  523 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  525 */         out.append("<tr>");
/*  526 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  527 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  531 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  533 */           String borderclass = "";
/*      */           
/*      */ 
/*  536 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  538 */           out.append("<tr>");
/*      */           
/*  540 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  541 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  542 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  545 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  548 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  553 */     out.append("</table><br>");
/*  554 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  555 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  557 */       List sLinks = secondLevelOfLinks[0];
/*  558 */       List sText = secondLevelOfLinks[1];
/*  559 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  562 */         out.append("<tr>");
/*  563 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  564 */         out.append("</tr>");
/*  565 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  567 */           String borderclass = "";
/*      */           
/*      */ 
/*  570 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  572 */           out.append("<tr>");
/*      */           
/*  574 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  575 */           if (sLinks.get(i).toString().length() == 0) {
/*  576 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  579 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  581 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  585 */     out.append("</table>");
/*  586 */     return out.toString();
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
/*  599 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  602 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  614 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  617 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  620 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  628 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  633 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  638 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  643 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  648 */     if (val != null)
/*      */     {
/*  650 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  654 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  659 */     if (val == null) {
/*  660 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  664 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  669 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  675 */     if (val != null)
/*      */     {
/*  677 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  681 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  687 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  692 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  696 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  701 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  706 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  711 */     String hostaddress = "";
/*  712 */     String ip = request.getHeader("x-forwarded-for");
/*  713 */     if (ip == null)
/*  714 */       ip = request.getRemoteAddr();
/*  715 */     InetAddress add = null;
/*  716 */     if (ip.equals("127.0.0.1")) {
/*  717 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  721 */       add = InetAddress.getByName(ip);
/*      */     }
/*  723 */     hostaddress = add.getHostName();
/*  724 */     if (hostaddress.indexOf('.') != -1) {
/*  725 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  726 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  730 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  735 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  741 */     if (severity == null)
/*      */     {
/*  743 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  745 */     if (severity.equals("5"))
/*      */     {
/*  747 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  749 */     if (severity.equals("1"))
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  756 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  761 */     ResultSet set = null;
/*  762 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  763 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  765 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  766 */       if (set.next()) { String str1;
/*  767 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  768 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  771 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  776 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  779 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  781 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  785 */     StringBuffer rca = new StringBuffer();
/*  786 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  787 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  790 */     int rcalength = key.length();
/*  791 */     String split = "6. ";
/*  792 */     int splitPresent = key.indexOf(split);
/*  793 */     String div1 = "";String div2 = "";
/*  794 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  796 */       if (rcalength > 180) {
/*  797 */         rca.append("<span class=\"rca-critical-text\">");
/*  798 */         getRCATrimmedText(key, rca);
/*  799 */         rca.append("</span>");
/*      */       } else {
/*  801 */         rca.append("<span class=\"rca-critical-text\">");
/*  802 */         rca.append(key);
/*  803 */         rca.append("</span>");
/*      */       }
/*  805 */       return rca.toString();
/*      */     }
/*  807 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  808 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  809 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  810 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  811 */     getRCATrimmedText(div1, rca);
/*  812 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  815 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  816 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  817 */     getRCATrimmedText(div2, rca);
/*  818 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  820 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  825 */     String[] st = msg.split("<br>");
/*  826 */     for (int i = 0; i < st.length; i++) {
/*  827 */       String s = st[i];
/*  828 */       if (s.length() > 180) {
/*  829 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  831 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  835 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  836 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  838 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  842 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  843 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  844 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  847 */       if (key == null) {
/*  848 */         return ret;
/*      */       }
/*      */       
/*  851 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  852 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  855 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  856 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  857 */       set = AMConnectionPool.executeQueryStmt(query);
/*  858 */       if (set.next())
/*      */       {
/*  860 */         String helpLink = set.getString("LINK");
/*  861 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  864 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  870 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  889 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  880 */         if (set != null) {
/*  881 */           AMConnectionPool.closeStatement(set);
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
/*  895 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  896 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  898 */       String entityStr = (String)keys.nextElement();
/*  899 */       String mmessage = temp.getProperty(entityStr);
/*  900 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  901 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  903 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  909 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   private void debug(String debugMessage)
/*      */   {
/*  922 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  932 */     String des = new String();
/*  933 */     while (str.indexOf(find) != -1) {
/*  934 */       des = des + str.substring(0, str.indexOf(find));
/*  935 */       des = des + replace;
/*  936 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  938 */     des = des + str;
/*  939 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  946 */       if (alert == null)
/*      */       {
/*  948 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  950 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  952 */         return "&nbsp;";
/*      */       }
/*      */       
/*  955 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  957 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  960 */       int rcalength = test.length();
/*  961 */       if (rcalength < 300)
/*      */       {
/*  963 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  967 */       StringBuffer out = new StringBuffer();
/*  968 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  969 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  970 */       out.append("</div>");
/*  971 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  973 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  978 */       ex.printStackTrace();
/*      */     }
/*  980 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  986 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  991 */     ArrayList attribIDs = new ArrayList();
/*  992 */     ArrayList resIDs = new ArrayList();
/*  993 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  995 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  997 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  999 */       String resourceid = "";
/* 1000 */       String resourceType = "";
/* 1001 */       if (type == 2) {
/* 1002 */         resourceid = (String)row.get(0);
/* 1003 */         resourceType = (String)row.get(3);
/*      */       }
/* 1005 */       else if (type == 3) {
/* 1006 */         resourceid = (String)row.get(0);
/* 1007 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1010 */         resourceid = (String)row.get(6);
/* 1011 */         resourceType = (String)row.get(7);
/*      */       }
/* 1013 */       resIDs.add(resourceid);
/* 1014 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1015 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1017 */       String healthentity = null;
/* 1018 */       String availentity = null;
/* 1019 */       if (healthid != null) {
/* 1020 */         healthentity = resourceid + "_" + healthid;
/* 1021 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1024 */       if (availid != null) {
/* 1025 */         availentity = resourceid + "_" + availid;
/* 1026 */         entitylist.add(availentity);
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
/* 1040 */     Properties alert = getStatus(entitylist);
/* 1041 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1046 */     int size = monitorList.size();
/*      */     
/* 1048 */     String[] severity = new String[size];
/*      */     
/* 1050 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1052 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1053 */       String resourceName1 = (String)row1.get(7);
/* 1054 */       String resourceid1 = (String)row1.get(6);
/* 1055 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1056 */       if (severity[j] == null)
/*      */       {
/* 1058 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1062 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1064 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1066 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1069 */         if (sev > 0) {
/* 1070 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1071 */           monitorList.set(k, monitorList.get(j));
/* 1072 */           monitorList.set(j, t);
/* 1073 */           String temp = severity[k];
/* 1074 */           severity[k] = severity[j];
/* 1075 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1081 */     int z = 0;
/* 1082 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1085 */       int i = 0;
/* 1086 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1089 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1093 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1097 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1099 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1102 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1106 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1109 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1110 */       String resourceName1 = (String)row1.get(7);
/* 1111 */       String resourceid1 = (String)row1.get(6);
/* 1112 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1113 */       if (hseverity[j] == null)
/*      */       {
/* 1115 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1122 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1125 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1128 */         if (hsev > 0) {
/* 1129 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1130 */           monitorList.set(k, monitorList.get(j));
/* 1131 */           monitorList.set(j, t);
/* 1132 */           String temp1 = hseverity[k];
/* 1133 */           hseverity[k] = hseverity[j];
/* 1134 */           hseverity[j] = temp1;
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
/* 1146 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1147 */     boolean forInventory = false;
/* 1148 */     String trdisplay = "none";
/* 1149 */     String plusstyle = "inline";
/* 1150 */     String minusstyle = "none";
/* 1151 */     String haidTopLevel = "";
/* 1152 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1154 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1156 */         haidTopLevel = request.getParameter("haid");
/* 1157 */         forInventory = true;
/* 1158 */         trdisplay = "table-row;";
/* 1159 */         plusstyle = "none";
/* 1160 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1167 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1170 */     ArrayList listtoreturn = new ArrayList();
/* 1171 */     StringBuffer toreturn = new StringBuffer();
/* 1172 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1173 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1174 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1176 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1178 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1179 */       String childresid = (String)singlerow.get(0);
/* 1180 */       String childresname = (String)singlerow.get(1);
/* 1181 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1182 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1183 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1184 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1185 */       String unmanagestatus = (String)singlerow.get(5);
/* 1186 */       String actionstatus = (String)singlerow.get(6);
/* 1187 */       String linkclass = "monitorgp-links";
/* 1188 */       String titleforres = childresname;
/* 1189 */       String titilechildresname = childresname;
/* 1190 */       String childimg = "/images/trcont.png";
/* 1191 */       String flag = "enable";
/* 1192 */       String dcstarted = (String)singlerow.get(8);
/* 1193 */       String configMonitor = "";
/* 1194 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1195 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1197 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1199 */       if (singlerow.get(7) != null)
/*      */       {
/* 1201 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1203 */       String haiGroupType = "0";
/* 1204 */       if ("HAI".equals(childtype))
/*      */       {
/* 1206 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1208 */       childimg = "/images/trend.png";
/* 1209 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1210 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1211 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1213 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1215 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1217 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1218 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1221 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1223 */         linkclass = "disabledtext";
/* 1224 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1226 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1227 */       String availmouseover = "";
/* 1228 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1230 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1232 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1233 */       String healthmouseover = "";
/* 1234 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1236 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1239 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1240 */       int spacing = 0;
/* 1241 */       if (level >= 1)
/*      */       {
/* 1243 */         spacing = 40 * level;
/*      */       }
/* 1245 */       if (childtype.equals("HAI"))
/*      */       {
/* 1247 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1248 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1249 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1251 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1252 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1253 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1254 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1255 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1256 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1257 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1258 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1259 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1260 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1261 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1263 */         if (!forInventory)
/*      */         {
/* 1265 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1268 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1270 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1272 */           actions = editlink + actions;
/*      */         }
/* 1274 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1276 */           actions = actions + associatelink;
/*      */         }
/* 1278 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1279 */         String arrowimg = "";
/* 1280 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1282 */           actions = "";
/* 1283 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1284 */           checkbox = "";
/* 1285 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1287 */         if (isIt360)
/*      */         {
/* 1289 */           actionimg = "";
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/*      */         }
/*      */         
/* 1295 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1297 */           actions = "";
/*      */         }
/* 1299 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1301 */           checkbox = "";
/*      */         }
/*      */         
/* 1304 */         String resourcelink = "";
/*      */         
/* 1306 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1308 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1312 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1315 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1321 */         if (!isIt360)
/*      */         {
/* 1323 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1327 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1330 */         toreturn.append("</tr>");
/* 1331 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1333 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1334 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1339 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1342 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1346 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1348 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1350 */             toreturn.append(assocMessage);
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1360 */         String resourcelink = null;
/* 1361 */         boolean hideEditLink = false;
/* 1362 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1364 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1365 */           hideEditLink = true;
/* 1366 */           if (isIt360)
/*      */           {
/* 1368 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1372 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1374 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1376 */           hideEditLink = true;
/* 1377 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1378 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1383 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1386 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1387 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1388 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1389 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1390 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1391 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1392 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1393 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1394 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1395 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1396 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1397 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1398 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1400 */         if (hideEditLink)
/*      */         {
/* 1402 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1404 */         if (!forInventory)
/*      */         {
/* 1406 */           removefromgroup = "";
/*      */         }
/* 1408 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1409 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1410 */           actions = actions + configcustomfields;
/*      */         }
/* 1412 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1414 */           actions = editlink + actions;
/*      */         }
/* 1416 */         String managedLink = "";
/* 1417 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1419 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1420 */           actions = "";
/* 1421 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1422 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1425 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1427 */           checkbox = "";
/*      */         }
/*      */         
/* 1430 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1432 */           actions = "";
/*      */         }
/* 1434 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1435 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1437 */         if (isIt360)
/*      */         {
/* 1439 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1443 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1447 */         if (!isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1455 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1458 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1465 */       StringBuilder toreturn = new StringBuilder();
/* 1466 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1467 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1468 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1469 */       String title = "";
/* 1470 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1471 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1472 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1473 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1475 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1477 */       else if ("5".equals(severity))
/*      */       {
/* 1479 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1483 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1485 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1486 */       toreturn.append(v);
/*      */       
/* 1488 */       toreturn.append(link);
/* 1489 */       if (severity == null)
/*      */       {
/* 1491 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1493 */       else if (severity.equals("5"))
/*      */       {
/* 1495 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1497 */       else if (severity.equals("4"))
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("1"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       toreturn.append("</a>");
/* 1511 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1515 */       ex.printStackTrace();
/*      */     }
/* 1517 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1524 */       StringBuilder toreturn = new StringBuilder();
/* 1525 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1526 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1527 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1528 */       if (message == null)
/*      */       {
/* 1530 */         message = "";
/*      */       }
/*      */       
/* 1533 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1534 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1536 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1537 */       toreturn.append(v);
/*      */       
/* 1539 */       toreturn.append(link);
/*      */       
/* 1541 */       if (severity == null)
/*      */       {
/* 1543 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1545 */       else if (severity.equals("5"))
/*      */       {
/* 1547 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1549 */       else if (severity.equals("1"))
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       toreturn.append("</a>");
/* 1559 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1565 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1568 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1569 */     if (invokeActions != null) {
/* 1570 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1571 */       while (iterator.hasNext()) {
/* 1572 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1573 */         if (actionmap.containsKey(actionid)) {
/* 1574 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1579 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1583 */     String actionLink = "";
/* 1584 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1585 */     String query = "";
/* 1586 */     ResultSet rs = null;
/* 1587 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1588 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1589 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1590 */       actionLink = "method=" + methodName;
/*      */     }
/* 1592 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1593 */       actionLink = methodName;
/*      */     }
/* 1595 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1596 */     Iterator itr = methodarglist.iterator();
/* 1597 */     boolean isfirstparam = true;
/* 1598 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1599 */     while (itr.hasNext()) {
/* 1600 */       HashMap argmap = (HashMap)itr.next();
/* 1601 */       String argtype = (String)argmap.get("TYPE");
/* 1602 */       String argname = (String)argmap.get("IDENTITY");
/* 1603 */       String paramname = (String)argmap.get("PARAMETER");
/* 1604 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1605 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1606 */         isfirstparam = false;
/* 1607 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1609 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1613 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1617 */         actionLink = actionLink + "&";
/*      */       }
/* 1619 */       String paramValue = null;
/* 1620 */       String tempargname = argname;
/* 1621 */       if (commonValues.getProperty(tempargname) != null) {
/* 1622 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1625 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1626 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1627 */           if (dbType.equals("mysql")) {
/* 1628 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1631 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1633 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1635 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1636 */             if (rs.next()) {
/* 1637 */               paramValue = rs.getString("VALUE");
/* 1638 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1642 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1646 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1649 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1654 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1655 */           paramValue = rowId;
/*      */         }
/* 1657 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1658 */           paramValue = managedObjectName;
/*      */         }
/* 1660 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1661 */           paramValue = resID;
/*      */         }
/* 1663 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1664 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1667 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1669 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1670 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1671 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1673 */     return actionLink;
/*      */   }
/*      */   
/* 1676 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1677 */     String dependentAttribute = null;
/* 1678 */     String align = "left";
/*      */     
/* 1680 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1681 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1682 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1683 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1684 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1685 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1686 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1687 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1688 */       align = "center";
/*      */     }
/*      */     
/* 1691 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1692 */     String actualdata = "";
/*      */     
/* 1694 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1695 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1696 */         actualdata = availValue;
/*      */       }
/* 1698 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1699 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1703 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1704 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1707 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1713 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1714 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1715 */       toreturn.append("<table>");
/* 1716 */       toreturn.append("<tr>");
/* 1717 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1718 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1719 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1720 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1721 */         String toolTip = "";
/* 1722 */         String hideClass = "";
/* 1723 */         String textStyle = "";
/* 1724 */         boolean isreferenced = true;
/* 1725 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1726 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1727 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1728 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1730 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1731 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1732 */           while (valueList.hasMoreTokens()) {
/* 1733 */             String dependentVal = valueList.nextToken();
/* 1734 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1735 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1736 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1738 */               toolTip = "";
/* 1739 */               hideClass = "";
/* 1740 */               isreferenced = false;
/* 1741 */               textStyle = "disabledtext";
/* 1742 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1746 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1747 */           toolTip = "";
/* 1748 */           hideClass = "";
/* 1749 */           isreferenced = false;
/* 1750 */           textStyle = "disabledtext";
/* 1751 */           if (dependentImageMap != null) {
/* 1752 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1753 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1756 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1761 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1762 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1763 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1764 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1765 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1767 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1768 */           if (isreferenced) {
/* 1769 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1773 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1774 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1775 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1776 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1777 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1778 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1780 */           toreturn.append("</span>");
/* 1781 */           toreturn.append("</a>");
/* 1782 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1785 */       toreturn.append("</tr>");
/* 1786 */       toreturn.append("</table>");
/* 1787 */       toreturn.append("</td>");
/*      */     } else {
/* 1789 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1792 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1796 */     String colTime = null;
/* 1797 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1798 */     if ((rows != null) && (rows.size() > 0)) {
/* 1799 */       Iterator<String> itr = rows.iterator();
/* 1800 */       String maxColQuery = "";
/* 1801 */       for (;;) { if (itr.hasNext()) {
/* 1802 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1803 */           ResultSet maxCol = null;
/*      */           try {
/* 1805 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1806 */             while (maxCol.next()) {
/* 1807 */               if (colTime == null) {
/* 1808 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1811 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1820 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1820 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1822 */               if (maxCol != null)
/* 1823 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1825 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1830 */     return colTime;
/*      */   }
/*      */   
/* 1833 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1834 */     tablename = null;
/* 1835 */     ResultSet rsTable = null;
/* 1836 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1838 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1839 */       while (rsTable.next()) {
/* 1840 */         tablename = rsTable.getString("DATATABLE");
/* 1841 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1842 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1855 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1846 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1849 */         if (rsTable != null)
/* 1850 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1852 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1858 */     String argsList = "";
/* 1859 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1861 */       if (showArgsMap.get(row) != null) {
/* 1862 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1863 */         if (showArgslist != null) {
/* 1864 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1865 */             if (argsList.trim().equals("")) {
/* 1866 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1869 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1876 */       e.printStackTrace();
/* 1877 */       return "";
/*      */     }
/* 1879 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1884 */     String argsList = "";
/* 1885 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1888 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1890 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1891 */         if (hideArgsList != null)
/*      */         {
/* 1893 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1895 */             if (argsList.trim().equals(""))
/*      */             {
/* 1897 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1901 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1909 */       ex.printStackTrace();
/*      */     }
/* 1911 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1915 */     StringBuilder toreturn = new StringBuilder();
/* 1916 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1923 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1924 */       Iterator itr = tActionList.iterator();
/* 1925 */       while (itr.hasNext()) {
/* 1926 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1927 */         String confirmmsg = "";
/* 1928 */         String link = "";
/* 1929 */         String isJSP = "NO";
/* 1930 */         HashMap tactionMap = (HashMap)itr.next();
/* 1931 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1932 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1933 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1934 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1935 */           (actionmap.containsKey(actionId))) {
/* 1936 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1937 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1938 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1939 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1940 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1942 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1948 */           if (isTableAction) {
/* 1949 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1952 */             tableName = "Link";
/* 1953 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1954 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1955 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1956 */             toreturn.append("</a></td>");
/*      */           }
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1967 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1973 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1975 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1976 */       Properties prop = (Properties)node.getUserObject();
/* 1977 */       String mgID = prop.getProperty("label");
/* 1978 */       String mgName = prop.getProperty("value");
/* 1979 */       String isParent = prop.getProperty("isParent");
/* 1980 */       int mgIDint = Integer.parseInt(mgID);
/* 1981 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1983 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1985 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1986 */       if (node.getChildCount() > 0)
/*      */       {
/* 1988 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1990 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1992 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1994 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1998 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2003 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2005 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2007 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2009 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2013 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2016 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2017 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2025 */       if (node.getChildCount() > 0)
/*      */       {
/* 2027 */         builder.append("<UL>");
/* 2028 */         printMGTree(node, builder);
/* 2029 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2034 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2035 */     StringBuffer toReturn = new StringBuffer();
/* 2036 */     String table = "-";
/*      */     try {
/* 2038 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2039 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2040 */       float total = 0.0F;
/* 2041 */       while (it.hasNext()) {
/* 2042 */         String attName = (String)it.next();
/* 2043 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2044 */         boolean roundOffData = false;
/* 2045 */         if ((data != null) && (!data.equals(""))) {
/* 2046 */           if (data.indexOf(",") != -1) {
/* 2047 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2050 */             float value = Float.parseFloat(data);
/* 2051 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2054 */             total += value;
/* 2055 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2058 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2063 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2064 */       while (attVsWidthList.hasNext()) {
/* 2065 */         String attName = (String)attVsWidthList.next();
/* 2066 */         String data = (String)attVsWidthProps.get(attName);
/* 2067 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2068 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2069 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2070 */         String className = (String)graphDetails.get("ClassName");
/* 2071 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2072 */         if (percentage < 1.0F)
/*      */         {
/* 2074 */           data = percentage + "";
/*      */         }
/* 2076 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2078 */       if (toReturn.length() > 0) {
/* 2079 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2083 */       e.printStackTrace();
/*      */     }
/* 2085 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2091 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2092 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2093 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2094 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2095 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2096 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2097 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2098 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2099 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2102 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2103 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2104 */       splitvalues[0] = multiplecondition.toString();
/* 2105 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2108 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2113 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2114 */     if (thresholdType != 3) {
/* 2115 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2116 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2117 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2118 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2119 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2120 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2122 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2123 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2124 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2125 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2126 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2127 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2129 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2130 */     if (updateSelected != null) {
/* 2131 */       updateSelected[0] = "selected";
/*      */     }
/* 2133 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2138 */       StringBuffer toreturn = new StringBuffer("");
/* 2139 */       if (commaSeparatedMsgId != null) {
/* 2140 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2141 */         int count = 0;
/* 2142 */         while (msgids.hasMoreTokens()) {
/* 2143 */           String id = msgids.nextToken();
/* 2144 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2145 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2146 */           count++;
/* 2147 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2148 */             if (toreturn.length() == 0) {
/* 2149 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2151 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2152 */             if (!image.trim().equals("")) {
/* 2153 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2155 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2156 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2159 */         if (toreturn.length() > 0) {
/* 2160 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2164 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2167 */       e.printStackTrace(); }
/* 2168 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2174 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2180 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2181 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2201 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2205 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2218 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2222 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2231 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2232 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2240 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2243 */     JspWriter out = null;
/* 2244 */     Object page = this;
/* 2245 */     JspWriter _jspx_out = null;
/* 2246 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2250 */       response.setContentType("text/html;charset=UTF-8");
/* 2251 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2253 */       _jspx_page_context = pageContext;
/* 2254 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2255 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2256 */       session = pageContext.getSession();
/* 2257 */       out = pageContext.getOut();
/* 2258 */       _jspx_out = out;
/*      */       
/* 2260 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2261 */       GetWLSGraph wlsGraph = null;
/* 2262 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2263 */       if (wlsGraph == null) {
/* 2264 */         wlsGraph = new GetWLSGraph();
/* 2265 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2267 */       out.write(10);
/* 2268 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2270 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2281 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2282 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2283 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2286 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2287 */         String available = null;
/* 2288 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2289 */         out.write(10);
/*      */         
/* 2291 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2302 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2303 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2304 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2307 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2308 */           String unavailable = null;
/* 2309 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2310 */           out.write(10);
/*      */           
/* 2312 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2323 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2324 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2325 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2328 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2329 */             String unmanaged = null;
/* 2330 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2331 */             out.write(10);
/*      */             
/* 2333 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2344 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2345 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2346 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2349 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2350 */               String scheduled = null;
/* 2351 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2352 */               out.write(10);
/*      */               
/* 2354 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2365 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2366 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2367 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2370 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2371 */                 String critical = null;
/* 2372 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2373 */                 out.write(10);
/*      */                 
/* 2375 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2386 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2387 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2388 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2391 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2392 */                   String clear = null;
/* 2393 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2394 */                   out.write(10);
/*      */                   
/* 2396 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2407 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2408 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2409 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2412 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2413 */                     String warning = null;
/* 2414 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2415 */                     out.write(10);
/* 2416 */                     out.write(10);
/*      */                     
/* 2418 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2419 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2421 */                     out.write(10);
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/*      */                     
/*      */ 
/* 2428 */                     String resourceid = request.getParameter("resourceid");
/* 2429 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=3");
/*      */                     
/* 2431 */                     ArrayList resIDs = (ArrayList)request.getAttribute("buffdata");
/* 2432 */                     resIDs.add(resourceid);
/*      */                     
/* 2434 */                     ArrayList<String> attribIDs = new ArrayList();
/* 2435 */                     for (int i = 2734; i < 2740; i++)
/*      */                     {
/* 2437 */                       attribIDs.add("" + i);
/*      */                     }
/* 2439 */                     attribIDs.add("2800");
/*      */                     
/* 2441 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/*      */ 
/*      */ 
/* 2445 */                     out.write("\n\n<br>\n\n");
/*      */                     
/* 2447 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2448 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2449 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2451 */                     _jspx_th_c_005fif_005f0.setTest("${disable}");
/* 2452 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2453 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2455 */                         out.write("\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  >\n        <tr>\n            <td  class='msg-status-tp-left-corn'></td>\n            <td class='msg-status-top-mid-bg'></td>\n            <td  class='msg-status-tp-right-corn'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-left-bg'></td>\n            <td  width='98%' class='msg-table-width'>\n                <table cellpadding='0' cellspacing='0' width='98%' border='0'>\n                    <tr>\n                        <td width='3%' class='msg-table-width-bg'>\n                            <img src='../images/icon_message_success.gif' alt='icon' height='20' width='20'></td>\n                        <td height=\"28\" class=\"msg-table-width\">&nbsp;");
/*      */                         
/* 2457 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2458 */                         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2459 */                         _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2461 */                         _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN,DEMO");
/* 2462 */                         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2463 */                         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                           for (;;)
/*      */                           {
/* 2466 */                             org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2467 */                             _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2468 */                             _jspx_th_bean_005fmessage_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                             
/* 2470 */                             _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.enable.admin.text");
/*      */                             
/* 2472 */                             _jspx_th_bean_005fmessage_005f0.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Pool"));
/* 2473 */                             int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 2474 */                             if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 2475 */                               this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                             }
/*      */                             
/* 2478 */                             this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 2479 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2480 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2484 */                         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2485 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                         }
/*      */                         
/* 2488 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2489 */                         out.write("\n                            ");
/*      */                         
/* 2491 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2492 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2493 */                         _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2495 */                         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,DEMO");
/* 2496 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2497 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;)
/*      */                           {
/* 2500 */                             org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2501 */                             _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2502 */                             _jspx_th_bean_005fmessage_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                             
/* 2504 */                             _jspx_th_bean_005fmessage_005f1.setKey("am.webclient.enable.text");
/*      */                             
/* 2506 */                             _jspx_th_bean_005fmessage_005f1.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Pool"));
/*      */                             
/* 2508 */                             _jspx_th_bean_005fmessage_005f1.setArg1(resourceid);
/* 2509 */                             int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 2510 */                             if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 2511 */                               this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1); return;
/*      */                             }
/*      */                             
/* 2514 */                             this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 2515 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2516 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2520 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2521 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                         }
/*      */                         
/* 2524 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2525 */                         out.write("</td>\n                    </tr>\n                </table>\n            </td>\n            <td class='msg-status-right-bg'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-btm-left-corn'>&nbsp;</td>\n            <td class='msg-status-btm-mid-bg'>&nbsp;</td>\n            <td class='msg-status-btm-right-corn'>&nbsp;</td>\n        </tr>\n    </table>\n");
/* 2526 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2527 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2531 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2532 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2535 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2536 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"conf-mon-data-table-border\" onMouseOver=\"$('#div1').css('opacity',1);\" onMouseOut=\"$('#div1').css('opacity',0.5)\">\n    <tr>\n        <td class=\"conf-mon-data-heading\" >");
/* 2537 */                       out.print(FormatUtil.getString("am.webclient.as400.pooldetails"));
/* 2538 */                       out.write("</td>\n        <td class=\"conf-mon-data-link\" align=\"right\">");
/* 2539 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2541 */                       out.write("</td>\n    </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"poolDetails\" class=\"lrbborder\" onMouseOver=\"$('#div1').css('opacity',1);\" onMouseOut=\"$('#div1').css('opacity',0.5)\">\n    <tr>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2542 */                       out.print(FormatUtil.getString("am.webclient.as400.poolname"));
/* 2543 */                       out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2544 */                       out.print(FormatUtil.getString("am.webclient.as400.poolsize"));
/* 2545 */                       out.write("(MB)</td>  ");
/* 2546 */                       out.write("\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2547 */                       out.print(FormatUtil.getString("am.webclient.as400.reservedsize"));
/* 2548 */                       out.write("(MB)</td>  ");
/* 2549 */                       out.write("\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2550 */                       out.print(FormatUtil.getString("am.webclient.as400.dbpages"));
/* 2551 */                       out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2552 */                       out.print(FormatUtil.getString("am.webclient.as400.dbfaults"));
/* 2553 */                       out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2554 */                       out.print(FormatUtil.getString("am.webclient.as400.nondbpages"));
/* 2555 */                       out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2556 */                       out.print(FormatUtil.getString("am.webclient.as400.nondbfaults"));
/* 2557 */                       out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"center\">&nbsp;</td>\n        ");
/* 2558 */                       if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                         return;
/* 2560 */                       out.write("\n\n    </tr>\n\n    ");
/*      */                       
/* 2562 */                       HashMap data = (HashMap)request.getAttribute("data");
/* 2563 */                       if (!data.isEmpty())
/*      */                       {
/*      */ 
/* 2566 */                         List k = (ArrayList)data.get("pool");
/* 2567 */                         for (int j = 0; j < k.size(); j++)
/*      */                         {
/* 2569 */                           Map p1 = new HashMap();
/* 2570 */                           p1 = (HashMap)k.get(j);
/* 2571 */                           String poolrid = (String)p1.get("POOLRID");
/* 2572 */                           String thresholdurl = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + poolrid + "&attributeIDs=2800,2734,2735,2736,2737,2738,2739&attributeToSelect=2800&redirectto=" + encodeurl;
/*      */                           
/*      */ 
/* 2575 */                           out.write("\n\n\n    <tr onmouseout=\"this.className='mondetailsHeader'; toggledivmo('");
/* 2576 */                           out.print(poolrid);
/* 2577 */                           out.write("',0)\" onmouseover=\"this.className='mondetailsHeaderHover'; toggledivmo('");
/* 2578 */                           out.print(poolrid);
/* 2579 */                           out.write("',1)\" class=\"mondetailsHeader\">\n\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2580 */                           out.print(p1.get("NAME"));
/* 2581 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\" >");
/* 2582 */                           out.print(FormatUtil.formatNumber(p1.get("SIZE")));
/* 2583 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2584 */                           out.print(FormatUtil.formatNumber(p1.get("RESERVED_SIZE")));
/* 2585 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2586 */                           out.print(FormatUtil.formatNumber(p1.get("DB_PAGES")));
/* 2587 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2588 */                           out.print(FormatUtil.formatNumber(p1.get("DB_FAULTS")));
/* 2589 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2590 */                           out.print(FormatUtil.formatNumber(p1.get("NON_DB_PAGES")));
/* 2591 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2592 */                           out.print(FormatUtil.formatNumber(p1.get("NON_DB_FAULTS")));
/* 2593 */                           out.write("</td>\n        <td align=\"center\" class=\"monitorinfoodd\" width=\"4%\" height=\"28\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2594 */                           out.print(poolrid);
/* 2595 */                           out.write("&attributeid=2800')\">");
/* 2596 */                           out.print(getSeverityImageForHealth(alert.getProperty(poolrid + "#" + "2800")));
/* 2597 */                           out.write("</a></td>\n        ");
/*      */                           
/* 2599 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2600 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2601 */                           _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                           
/* 2603 */                           _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,DEMO");
/* 2604 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2605 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 2607 */                               out.write("<td align=\"center\" class=\"monitorinfoodd\" height=\"28\"><div style=\"visibility: hidden;\" id=\"");
/* 2608 */                               out.print(poolrid);
/* 2609 */                               out.write("\" ><a href=");
/*      */                               
/* 2611 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2612 */                               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2613 */                               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                               
/* 2615 */                               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2616 */                               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2617 */                               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                 for (;;) {
/* 2619 */                                   out.write(34);
/* 2620 */                                   out.print(thresholdurl);
/* 2621 */                                   out.write(34);
/* 2622 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2623 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2627 */                               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2628 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                               }
/*      */                               
/* 2631 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2632 */                               if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                 return;
/* 2634 */                               out.write(" class=\"staticlinks\">  <img title=\"");
/* 2635 */                               out.print(ALERTCONFIG_TEXT);
/* 2636 */                               out.write("\" src=\"/images/icon_associateaction.gif\" alt=\"");
/* 2637 */                               out.print(ALERTCONFIG_TEXT);
/* 2638 */                               out.write("\" border=\"0\" hspace=\"5\" align=\"absmiddle\" ></a>\n        </div></td>");
/* 2639 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2640 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2644 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2645 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                           }
/*      */                           
/* 2648 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2649 */                           out.write("\n    </tr>\n\n\n    ");
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2656 */                         out.write("\n    <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n        <td colspan=\"10\" class=\"monitorinfoodd\" align=\"center\"><b>");
/* 2657 */                         out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 2658 */                         out.write("</b></td>\n    </tr>\n    ");
/*      */                       }
/*      */                       
/* 2661 */                       out.write("\n\n</table>\n\n");
/*      */                       
/* 2663 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2664 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2665 */                       _jspx_th_c_005fif_005f1.setParent(null);
/*      */                       
/* 2667 */                       _jspx_th_c_005fif_005f1.setTest("${not disable}");
/* 2668 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2669 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 2671 */                           out.write("\n\n    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n            <td width=\"50%\" valign=\"top\">\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2672 */                           out.print(FormatUtil.getString("am.webclient.as400.poolsize"));
/* 2673 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2675 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_POOL");
/*      */                           
/* 2677 */                           out.write("\n                            ");
/* 2678 */                           if (_jspx_meth_awolf_005fbarchart_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2680 */                           out.write(" </td>   ");
/* 2681 */                           out.write("\n                    </tr>\n                </table>\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2682 */                           out.print(FormatUtil.getString("am.webclient.as400.dbpages"));
/* 2683 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2685 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_DBPAGES");
/*      */                           
/* 2687 */                           out.write("\n                            ");
/* 2688 */                           if (_jspx_meth_awolf_005fbarchart_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2690 */                           out.write(" </td>   ");
/* 2691 */                           out.write("\n                    </tr>\n                </table>\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2692 */                           out.print(FormatUtil.getString("am.webclient.as400.dbfaults"));
/* 2693 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2695 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_DBFAULTS");
/*      */                           
/* 2697 */                           out.write("\n                            ");
/* 2698 */                           if (_jspx_meth_awolf_005fbarchart_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2700 */                           out.write(" </td>   ");
/* 2701 */                           out.write("\n                    </tr>\n                </table>\n            </td>\n\n\n\n            <td width=\"1%\">&nbsp;</td>\n\n\n\n            <td width=\"49%\" valign=\"top\">\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2702 */                           out.print(FormatUtil.getString("am.webclient.as400.reservedsize"));
/* 2703 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2705 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_RESIZE");
/*      */                           
/* 2707 */                           out.write("\n                            ");
/* 2708 */                           if (_jspx_meth_awolf_005fbarchart_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2710 */                           out.write(" </td>\n                    </tr>\n                </table>\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2711 */                           out.print(FormatUtil.getString("am.webclient.as400.nondbpages"));
/* 2712 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2714 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_NDBPAGES");
/*      */                           
/* 2716 */                           out.write("\n                            ");
/* 2717 */                           if (_jspx_meth_awolf_005fbarchart_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2719 */                           out.write(" </td>\n                    </tr>\n                </table>\n\n                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"conf-mon-table\">\n                    <tr>\n                        <td class=\"conf-mon-heading\" >");
/* 2720 */                           out.print(FormatUtil.getString("am.webclient.as400.nondbfaults"));
/* 2721 */                           out.write("</td>\n                    </tr>\n                    <tr>\n                        <td align=\"center\" style=\"padding : 5px 0px 0px 0px;\">\n                            ");
/*      */                           
/* 2723 */                           wlsGraph.setParam(request.getParameter("resourceid"), "AS400_NDBFAULTS");
/*      */                           
/* 2725 */                           out.write("\n                            ");
/* 2726 */                           if (_jspx_meth_awolf_005fbarchart_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2728 */                           out.write(" </td>\n                    </tr>\n                </table>\n\n            </td>\n        </tr>\n    </table>\n\n");
/* 2729 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2730 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2734 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2735 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                       }
/*      */                       else {
/* 2738 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2739 */                         out.write("\n<script language=\"javascript\">\n\n\n    SORTTABLENAME = 'poolDetails'; //No I18N\n    var numberOfColumnsToBeSorted = 7;\n\n    sortables_init(numberOfColumnsToBeSorted,false,false,true);\n\n\n</script>\n\n");
/* 2740 */                         if (_jspx_meth_c_005fset_005f0(_jspx_page_context)) return;
/*      */                       }
/*      */                     }
/* 2743 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2744 */         out = _jspx_out;
/* 2745 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2746 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2747 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2750 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2756 */     PageContext pageContext = _jspx_page_context;
/* 2757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2759 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2760 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2761 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 2763 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,DEMO");
/* 2764 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2765 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2767 */         out.write("<div style=\"opacity: 0.5;\" id=\"div1\" ><a onmouseover=\"ddrivetip(this,event,'");
/* 2768 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2769 */           return true;
/* 2770 */         out.write(32);
/* 2771 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2772 */           return true;
/* 2773 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=getenabledetails&type=AS400/iSeries&resourceid=");
/* 2774 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2775 */           return true;
/* 2776 */         out.write("',850,400,0,0)\"><img title=\"");
/* 2777 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2778 */           return true;
/* 2779 */         out.write("\" src=\"/images/icon_disable.gif\" style=\"position:relative; top:1px; right:5px;\"/>");
/* 2780 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2781 */           return true;
/* 2782 */         out.write("</a>&nbsp;\n        </div>");
/* 2783 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2784 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2788 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2789 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2790 */       return true;
/*      */     }
/* 2792 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2798 */     PageContext pageContext = _jspx_page_context;
/* 2799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2801 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2802 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2803 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2805 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.tooltip");
/* 2806 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2807 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2808 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2809 */       return true;
/*      */     }
/* 2811 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2817 */     PageContext pageContext = _jspx_page_context;
/* 2818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2820 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2821 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2822 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2824 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.disable.pool");
/* 2825 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2826 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2827 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2828 */       return true;
/*      */     }
/* 2830 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2836 */     PageContext pageContext = _jspx_page_context;
/* 2837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2839 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2840 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2841 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2843 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 2844 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2845 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2847 */       return true;
/*      */     }
/* 2849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2855 */     PageContext pageContext = _jspx_page_context;
/* 2856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2858 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2859 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2860 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2862 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.disable.pool");
/* 2863 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2864 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2865 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2866 */       return true;
/*      */     }
/* 2868 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2874 */     PageContext pageContext = _jspx_page_context;
/* 2875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2877 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f3 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2878 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2879 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2881 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.disable.pool");
/* 2882 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2883 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 2884 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2885 */       return true;
/*      */     }
/* 2887 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 2888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2893 */     PageContext pageContext = _jspx_page_context;
/* 2894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2896 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2897 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2898 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 2900 */     _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,DEMO");
/* 2901 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2902 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 2904 */         out.write("\n            <td class=\"monitorinfoodd\" align=\"center\" >&nbsp;</td>\n        ");
/* 2905 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2906 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2910 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2911 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2912 */       return true;
/*      */     }
/* 2914 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2920 */     PageContext pageContext = _jspx_page_context;
/* 2921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2923 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2924 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2925 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 2927 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 2928 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2929 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 2931 */         out.write("\"javascript:alertUser();\"");
/* 2932 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2937 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2938 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2939 */       return true;
/*      */     }
/* 2941 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2947 */     PageContext pageContext = _jspx_page_context;
/* 2948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2950 */     BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 2951 */     _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 2952 */     _jspx_th_awolf_005fbarchart_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2954 */     _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 2956 */     _jspx_th_awolf_005fbarchart_005f0.setWidth("600");
/*      */     
/* 2958 */     _jspx_th_awolf_005fbarchart_005f0.setHeight("280");
/*      */     
/* 2960 */     _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */     
/* 2962 */     _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel("Pool");
/*      */     
/* 2964 */     _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel("");
/* 2965 */     int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 2966 */     if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 2967 */       if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2968 */         out = _jspx_page_context.pushBody();
/* 2969 */         _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 2970 */         _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2973 */         out.write("\n                                ");
/* 2974 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fbarchart_005f0, _jspx_page_context))
/* 2975 */           return true;
/* 2976 */         out.write(9);
/* 2977 */         out.write("\n                            ");
/* 2978 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 2979 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2982 */       if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 2983 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2986 */     if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 2987 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2988 */       return true;
/*      */     }
/* 2990 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 2991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fbarchart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2996 */     PageContext pageContext = _jspx_page_context;
/* 2997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2999 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3000 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3001 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fbarchart_005f0);
/*      */     
/* 3003 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3004 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3005 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3006 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3007 */         out = _jspx_page_context.pushBody();
/* 3008 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3009 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3012 */         out.write("\n                                    ");
/* 3013 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3014 */           return true;
/* 3015 */         out.write("\n                                    ");
/* 3016 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3017 */           return true;
/* 3018 */         out.write("\n                                    ");
/* 3019 */         if (_jspx_meth_awolf_005fparam_005f2(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3020 */           return true;
/* 3021 */         out.write("\n                                    ");
/* 3022 */         if (_jspx_meth_awolf_005fparam_005f3(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3023 */           return true;
/* 3024 */         out.write("\n                                ");
/* 3025 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3029 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3030 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3033 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3034 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3035 */       return true;
/*      */     }
/* 3037 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3043 */     PageContext pageContext = _jspx_page_context;
/* 3044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3046 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3047 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3048 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3050 */     _jspx_th_awolf_005fparam_005f0.setName("0");
/*      */     
/* 3052 */     _jspx_th_awolf_005fparam_005f0.setValue("#C6454A");
/* 3053 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3054 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3055 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3056 */       return true;
/*      */     }
/* 3058 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3064 */     PageContext pageContext = _jspx_page_context;
/* 3065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3067 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3068 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3069 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3071 */     _jspx_th_awolf_005fparam_005f1.setName("1");
/*      */     
/* 3073 */     _jspx_th_awolf_005fparam_005f1.setValue("#00FF00");
/* 3074 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3075 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3076 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3077 */       return true;
/*      */     }
/* 3079 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3080 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f2(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3085 */     PageContext pageContext = _jspx_page_context;
/* 3086 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3088 */     AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3089 */     _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 3090 */     _jspx_th_awolf_005fparam_005f2.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3092 */     _jspx_th_awolf_005fparam_005f2.setName("2");
/*      */     
/* 3094 */     _jspx_th_awolf_005fparam_005f2.setValue("#FE7301");
/* 3095 */     int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 3096 */     if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 3097 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3098 */       return true;
/*      */     }
/* 3100 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 3101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f3(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3106 */     PageContext pageContext = _jspx_page_context;
/* 3107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3109 */     AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3110 */     _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 3111 */     _jspx_th_awolf_005fparam_005f3.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3113 */     _jspx_th_awolf_005fparam_005f3.setName("3");
/*      */     
/* 3115 */     _jspx_th_awolf_005fparam_005f3.setValue("#589037");
/* 3116 */     int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 3117 */     if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 3118 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3119 */       return true;
/*      */     }
/* 3121 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 3122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3127 */     PageContext pageContext = _jspx_page_context;
/* 3128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3130 */     BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3131 */     _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 3132 */     _jspx_th_awolf_005fbarchart_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3134 */     _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("wlsGraph");
/*      */     
/* 3136 */     _jspx_th_awolf_005fbarchart_005f1.setWidth("600");
/*      */     
/* 3138 */     _jspx_th_awolf_005fbarchart_005f1.setHeight("280");
/*      */     
/* 3140 */     _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*      */     
/* 3142 */     _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel("Pool");
/*      */     
/* 3144 */     _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel("");
/* 3145 */     int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 3146 */     if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 3147 */       if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3148 */         out = _jspx_page_context.pushBody();
/* 3149 */         _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 3150 */         _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3153 */         out.write("\n                                ");
/* 3154 */         if (_jspx_meth_awolf_005fmap_005f1(_jspx_th_awolf_005fbarchart_005f1, _jspx_page_context))
/* 3155 */           return true;
/* 3156 */         out.write(32);
/* 3157 */         out.write(32);
/* 3158 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 3159 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3162 */       if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3163 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3166 */     if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 3167 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3168 */       return true;
/*      */     }
/* 3170 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f1(JspTag _jspx_th_awolf_005fbarchart_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3176 */     PageContext pageContext = _jspx_page_context;
/* 3177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3179 */     Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3180 */     _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 3181 */     _jspx_th_awolf_005fmap_005f1.setParent((Tag)_jspx_th_awolf_005fbarchart_005f1);
/*      */     
/* 3183 */     _jspx_th_awolf_005fmap_005f1.setId("color");
/* 3184 */     int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 3185 */     if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 3186 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3187 */         out = _jspx_page_context.pushBody();
/* 3188 */         _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 3189 */         _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3192 */         out.write("\n                                    ");
/* 3193 */         if (_jspx_meth_awolf_005fparam_005f4(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3194 */           return true;
/* 3195 */         out.write("\n                                    ");
/* 3196 */         if (_jspx_meth_awolf_005fparam_005f5(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3197 */           return true;
/* 3198 */         out.write("\n                                    ");
/* 3199 */         if (_jspx_meth_awolf_005fparam_005f6(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3200 */           return true;
/* 3201 */         out.write("\n                                    ");
/* 3202 */         if (_jspx_meth_awolf_005fparam_005f7(_jspx_th_awolf_005fmap_005f1, _jspx_page_context))
/* 3203 */           return true;
/* 3204 */         out.write("\n                                ");
/* 3205 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 3206 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3209 */       if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 3210 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3213 */     if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 3214 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 3215 */       return true;
/*      */     }
/* 3217 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 3218 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f4(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3223 */     PageContext pageContext = _jspx_page_context;
/* 3224 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3226 */     AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3227 */     _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 3228 */     _jspx_th_awolf_005fparam_005f4.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3230 */     _jspx_th_awolf_005fparam_005f4.setName("0");
/*      */     
/* 3232 */     _jspx_th_awolf_005fparam_005f4.setValue("#C6454A");
/* 3233 */     int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 3234 */     if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 3235 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 3236 */       return true;
/*      */     }
/* 3238 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 3239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f5(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3244 */     PageContext pageContext = _jspx_page_context;
/* 3245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3247 */     AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3248 */     _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 3249 */     _jspx_th_awolf_005fparam_005f5.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3251 */     _jspx_th_awolf_005fparam_005f5.setName("1");
/*      */     
/* 3253 */     _jspx_th_awolf_005fparam_005f5.setValue("#00FF00");
/* 3254 */     int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 3255 */     if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 3256 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 3257 */       return true;
/*      */     }
/* 3259 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 3260 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f6(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3265 */     PageContext pageContext = _jspx_page_context;
/* 3266 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3268 */     AMParam _jspx_th_awolf_005fparam_005f6 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3269 */     _jspx_th_awolf_005fparam_005f6.setPageContext(_jspx_page_context);
/* 3270 */     _jspx_th_awolf_005fparam_005f6.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3272 */     _jspx_th_awolf_005fparam_005f6.setName("2");
/*      */     
/* 3274 */     _jspx_th_awolf_005fparam_005f6.setValue("#FE7301");
/* 3275 */     int _jspx_eval_awolf_005fparam_005f6 = _jspx_th_awolf_005fparam_005f6.doStartTag();
/* 3276 */     if (_jspx_th_awolf_005fparam_005f6.doEndTag() == 5) {
/* 3277 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6);
/* 3278 */       return true;
/*      */     }
/* 3280 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6);
/* 3281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f7(JspTag _jspx_th_awolf_005fmap_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3286 */     PageContext pageContext = _jspx_page_context;
/* 3287 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3289 */     AMParam _jspx_th_awolf_005fparam_005f7 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3290 */     _jspx_th_awolf_005fparam_005f7.setPageContext(_jspx_page_context);
/* 3291 */     _jspx_th_awolf_005fparam_005f7.setParent((Tag)_jspx_th_awolf_005fmap_005f1);
/*      */     
/* 3293 */     _jspx_th_awolf_005fparam_005f7.setName("3");
/*      */     
/* 3295 */     _jspx_th_awolf_005fparam_005f7.setValue("#589037");
/* 3296 */     int _jspx_eval_awolf_005fparam_005f7 = _jspx_th_awolf_005fparam_005f7.doStartTag();
/* 3297 */     if (_jspx_th_awolf_005fparam_005f7.doEndTag() == 5) {
/* 3298 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f7);
/* 3299 */       return true;
/*      */     }
/* 3301 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f7);
/* 3302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3307 */     PageContext pageContext = _jspx_page_context;
/* 3308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3310 */     BarChart _jspx_th_awolf_005fbarchart_005f2 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3311 */     _jspx_th_awolf_005fbarchart_005f2.setPageContext(_jspx_page_context);
/* 3312 */     _jspx_th_awolf_005fbarchart_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3314 */     _jspx_th_awolf_005fbarchart_005f2.setDataSetProducer("wlsGraph");
/*      */     
/* 3316 */     _jspx_th_awolf_005fbarchart_005f2.setWidth("600");
/*      */     
/* 3318 */     _jspx_th_awolf_005fbarchart_005f2.setHeight("280");
/*      */     
/* 3320 */     _jspx_th_awolf_005fbarchart_005f2.setLegend("false");
/*      */     
/* 3322 */     _jspx_th_awolf_005fbarchart_005f2.setXaxisLabel("Pool");
/*      */     
/* 3324 */     _jspx_th_awolf_005fbarchart_005f2.setYaxisLabel("");
/* 3325 */     int _jspx_eval_awolf_005fbarchart_005f2 = _jspx_th_awolf_005fbarchart_005f2.doStartTag();
/* 3326 */     if (_jspx_eval_awolf_005fbarchart_005f2 != 0) {
/* 3327 */       if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 3328 */         out = _jspx_page_context.pushBody();
/* 3329 */         _jspx_th_awolf_005fbarchart_005f2.setBodyContent((BodyContent)out);
/* 3330 */         _jspx_th_awolf_005fbarchart_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3333 */         out.write("\n                                ");
/* 3334 */         if (_jspx_meth_awolf_005fmap_005f2(_jspx_th_awolf_005fbarchart_005f2, _jspx_page_context))
/* 3335 */           return true;
/* 3336 */         out.write("\t    ");
/* 3337 */         out.write("\n                            ");
/* 3338 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f2.doAfterBody();
/* 3339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3342 */       if (_jspx_eval_awolf_005fbarchart_005f2 != 1) {
/* 3343 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3346 */     if (_jspx_th_awolf_005fbarchart_005f2.doEndTag() == 5) {
/* 3347 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2);
/* 3348 */       return true;
/*      */     }
/* 3350 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f2);
/* 3351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f2(JspTag _jspx_th_awolf_005fbarchart_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3356 */     PageContext pageContext = _jspx_page_context;
/* 3357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3359 */     Property _jspx_th_awolf_005fmap_005f2 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3360 */     _jspx_th_awolf_005fmap_005f2.setPageContext(_jspx_page_context);
/* 3361 */     _jspx_th_awolf_005fmap_005f2.setParent((Tag)_jspx_th_awolf_005fbarchart_005f2);
/*      */     
/* 3363 */     _jspx_th_awolf_005fmap_005f2.setId("color");
/* 3364 */     int _jspx_eval_awolf_005fmap_005f2 = _jspx_th_awolf_005fmap_005f2.doStartTag();
/* 3365 */     if (_jspx_eval_awolf_005fmap_005f2 != 0) {
/* 3366 */       if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 3367 */         out = _jspx_page_context.pushBody();
/* 3368 */         _jspx_th_awolf_005fmap_005f2.setBodyContent((BodyContent)out);
/* 3369 */         _jspx_th_awolf_005fmap_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3372 */         out.write("\n                                    ");
/* 3373 */         if (_jspx_meth_awolf_005fparam_005f8(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 3374 */           return true;
/* 3375 */         out.write("\n                                    ");
/* 3376 */         if (_jspx_meth_awolf_005fparam_005f9(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 3377 */           return true;
/* 3378 */         out.write("\n                                    ");
/* 3379 */         if (_jspx_meth_awolf_005fparam_005f10(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 3380 */           return true;
/* 3381 */         out.write("\n                                    ");
/* 3382 */         if (_jspx_meth_awolf_005fparam_005f11(_jspx_th_awolf_005fmap_005f2, _jspx_page_context))
/* 3383 */           return true;
/* 3384 */         out.write("\n                                ");
/* 3385 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f2.doAfterBody();
/* 3386 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3389 */       if (_jspx_eval_awolf_005fmap_005f2 != 1) {
/* 3390 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3393 */     if (_jspx_th_awolf_005fmap_005f2.doEndTag() == 5) {
/* 3394 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2);
/* 3395 */       return true;
/*      */     }
/* 3397 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f2);
/* 3398 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f8(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3403 */     PageContext pageContext = _jspx_page_context;
/* 3404 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3406 */     AMParam _jspx_th_awolf_005fparam_005f8 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3407 */     _jspx_th_awolf_005fparam_005f8.setPageContext(_jspx_page_context);
/* 3408 */     _jspx_th_awolf_005fparam_005f8.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 3410 */     _jspx_th_awolf_005fparam_005f8.setName("0");
/*      */     
/* 3412 */     _jspx_th_awolf_005fparam_005f8.setValue("#C6454A");
/* 3413 */     int _jspx_eval_awolf_005fparam_005f8 = _jspx_th_awolf_005fparam_005f8.doStartTag();
/* 3414 */     if (_jspx_th_awolf_005fparam_005f8.doEndTag() == 5) {
/* 3415 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f8);
/* 3416 */       return true;
/*      */     }
/* 3418 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f8);
/* 3419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f9(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3424 */     PageContext pageContext = _jspx_page_context;
/* 3425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3427 */     AMParam _jspx_th_awolf_005fparam_005f9 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3428 */     _jspx_th_awolf_005fparam_005f9.setPageContext(_jspx_page_context);
/* 3429 */     _jspx_th_awolf_005fparam_005f9.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 3431 */     _jspx_th_awolf_005fparam_005f9.setName("1");
/*      */     
/* 3433 */     _jspx_th_awolf_005fparam_005f9.setValue("#00FF00");
/* 3434 */     int _jspx_eval_awolf_005fparam_005f9 = _jspx_th_awolf_005fparam_005f9.doStartTag();
/* 3435 */     if (_jspx_th_awolf_005fparam_005f9.doEndTag() == 5) {
/* 3436 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f9);
/* 3437 */       return true;
/*      */     }
/* 3439 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f9);
/* 3440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f10(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3445 */     PageContext pageContext = _jspx_page_context;
/* 3446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3448 */     AMParam _jspx_th_awolf_005fparam_005f10 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3449 */     _jspx_th_awolf_005fparam_005f10.setPageContext(_jspx_page_context);
/* 3450 */     _jspx_th_awolf_005fparam_005f10.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 3452 */     _jspx_th_awolf_005fparam_005f10.setName("2");
/*      */     
/* 3454 */     _jspx_th_awolf_005fparam_005f10.setValue("#FE7301");
/* 3455 */     int _jspx_eval_awolf_005fparam_005f10 = _jspx_th_awolf_005fparam_005f10.doStartTag();
/* 3456 */     if (_jspx_th_awolf_005fparam_005f10.doEndTag() == 5) {
/* 3457 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f10);
/* 3458 */       return true;
/*      */     }
/* 3460 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f10);
/* 3461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f11(JspTag _jspx_th_awolf_005fmap_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3466 */     PageContext pageContext = _jspx_page_context;
/* 3467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3469 */     AMParam _jspx_th_awolf_005fparam_005f11 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3470 */     _jspx_th_awolf_005fparam_005f11.setPageContext(_jspx_page_context);
/* 3471 */     _jspx_th_awolf_005fparam_005f11.setParent((Tag)_jspx_th_awolf_005fmap_005f2);
/*      */     
/* 3473 */     _jspx_th_awolf_005fparam_005f11.setName("3");
/*      */     
/* 3475 */     _jspx_th_awolf_005fparam_005f11.setValue("#589037");
/* 3476 */     int _jspx_eval_awolf_005fparam_005f11 = _jspx_th_awolf_005fparam_005f11.doStartTag();
/* 3477 */     if (_jspx_th_awolf_005fparam_005f11.doEndTag() == 5) {
/* 3478 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f11);
/* 3479 */       return true;
/*      */     }
/* 3481 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f11);
/* 3482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3487 */     PageContext pageContext = _jspx_page_context;
/* 3488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3490 */     BarChart _jspx_th_awolf_005fbarchart_005f3 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3491 */     _jspx_th_awolf_005fbarchart_005f3.setPageContext(_jspx_page_context);
/* 3492 */     _jspx_th_awolf_005fbarchart_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3494 */     _jspx_th_awolf_005fbarchart_005f3.setDataSetProducer("wlsGraph");
/*      */     
/* 3496 */     _jspx_th_awolf_005fbarchart_005f3.setWidth("600");
/*      */     
/* 3498 */     _jspx_th_awolf_005fbarchart_005f3.setHeight("280");
/*      */     
/* 3500 */     _jspx_th_awolf_005fbarchart_005f3.setLegend("false");
/*      */     
/* 3502 */     _jspx_th_awolf_005fbarchart_005f3.setXaxisLabel("Pool");
/*      */     
/* 3504 */     _jspx_th_awolf_005fbarchart_005f3.setYaxisLabel("");
/* 3505 */     int _jspx_eval_awolf_005fbarchart_005f3 = _jspx_th_awolf_005fbarchart_005f3.doStartTag();
/* 3506 */     if (_jspx_eval_awolf_005fbarchart_005f3 != 0) {
/* 3507 */       if (_jspx_eval_awolf_005fbarchart_005f3 != 1) {
/* 3508 */         out = _jspx_page_context.pushBody();
/* 3509 */         _jspx_th_awolf_005fbarchart_005f3.setBodyContent((BodyContent)out);
/* 3510 */         _jspx_th_awolf_005fbarchart_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3513 */         out.write("\n                                ");
/* 3514 */         if (_jspx_meth_awolf_005fmap_005f3(_jspx_th_awolf_005fbarchart_005f3, _jspx_page_context))
/* 3515 */           return true;
/* 3516 */         out.write("\n                            ");
/* 3517 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f3.doAfterBody();
/* 3518 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3521 */       if (_jspx_eval_awolf_005fbarchart_005f3 != 1) {
/* 3522 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3525 */     if (_jspx_th_awolf_005fbarchart_005f3.doEndTag() == 5) {
/* 3526 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f3);
/* 3527 */       return true;
/*      */     }
/* 3529 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f3);
/* 3530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f3(JspTag _jspx_th_awolf_005fbarchart_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3535 */     PageContext pageContext = _jspx_page_context;
/* 3536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3538 */     Property _jspx_th_awolf_005fmap_005f3 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3539 */     _jspx_th_awolf_005fmap_005f3.setPageContext(_jspx_page_context);
/* 3540 */     _jspx_th_awolf_005fmap_005f3.setParent((Tag)_jspx_th_awolf_005fbarchart_005f3);
/*      */     
/* 3542 */     _jspx_th_awolf_005fmap_005f3.setId("color");
/* 3543 */     int _jspx_eval_awolf_005fmap_005f3 = _jspx_th_awolf_005fmap_005f3.doStartTag();
/* 3544 */     if (_jspx_eval_awolf_005fmap_005f3 != 0) {
/* 3545 */       if (_jspx_eval_awolf_005fmap_005f3 != 1) {
/* 3546 */         out = _jspx_page_context.pushBody();
/* 3547 */         _jspx_th_awolf_005fmap_005f3.setBodyContent((BodyContent)out);
/* 3548 */         _jspx_th_awolf_005fmap_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3551 */         out.write("\n                                    ");
/* 3552 */         if (_jspx_meth_awolf_005fparam_005f12(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 3553 */           return true;
/* 3554 */         out.write("\n                                    ");
/* 3555 */         if (_jspx_meth_awolf_005fparam_005f13(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 3556 */           return true;
/* 3557 */         out.write("\n                                    ");
/* 3558 */         if (_jspx_meth_awolf_005fparam_005f14(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 3559 */           return true;
/* 3560 */         out.write("\n                                    ");
/* 3561 */         if (_jspx_meth_awolf_005fparam_005f15(_jspx_th_awolf_005fmap_005f3, _jspx_page_context))
/* 3562 */           return true;
/* 3563 */         out.write("\n                                ");
/* 3564 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f3.doAfterBody();
/* 3565 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3568 */       if (_jspx_eval_awolf_005fmap_005f3 != 1) {
/* 3569 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3572 */     if (_jspx_th_awolf_005fmap_005f3.doEndTag() == 5) {
/* 3573 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f3);
/* 3574 */       return true;
/*      */     }
/* 3576 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f3);
/* 3577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f12(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3582 */     PageContext pageContext = _jspx_page_context;
/* 3583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3585 */     AMParam _jspx_th_awolf_005fparam_005f12 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3586 */     _jspx_th_awolf_005fparam_005f12.setPageContext(_jspx_page_context);
/* 3587 */     _jspx_th_awolf_005fparam_005f12.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 3589 */     _jspx_th_awolf_005fparam_005f12.setName("0");
/*      */     
/* 3591 */     _jspx_th_awolf_005fparam_005f12.setValue("#C6454A");
/* 3592 */     int _jspx_eval_awolf_005fparam_005f12 = _jspx_th_awolf_005fparam_005f12.doStartTag();
/* 3593 */     if (_jspx_th_awolf_005fparam_005f12.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f12);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f12);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f13(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     AMParam _jspx_th_awolf_005fparam_005f13 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3607 */     _jspx_th_awolf_005fparam_005f13.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_awolf_005fparam_005f13.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 3610 */     _jspx_th_awolf_005fparam_005f13.setName("1");
/*      */     
/* 3612 */     _jspx_th_awolf_005fparam_005f13.setValue("#00FF00");
/* 3613 */     int _jspx_eval_awolf_005fparam_005f13 = _jspx_th_awolf_005fparam_005f13.doStartTag();
/* 3614 */     if (_jspx_th_awolf_005fparam_005f13.doEndTag() == 5) {
/* 3615 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f13);
/* 3616 */       return true;
/*      */     }
/* 3618 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f13);
/* 3619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f14(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3624 */     PageContext pageContext = _jspx_page_context;
/* 3625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3627 */     AMParam _jspx_th_awolf_005fparam_005f14 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3628 */     _jspx_th_awolf_005fparam_005f14.setPageContext(_jspx_page_context);
/* 3629 */     _jspx_th_awolf_005fparam_005f14.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 3631 */     _jspx_th_awolf_005fparam_005f14.setName("2");
/*      */     
/* 3633 */     _jspx_th_awolf_005fparam_005f14.setValue("#FE7301");
/* 3634 */     int _jspx_eval_awolf_005fparam_005f14 = _jspx_th_awolf_005fparam_005f14.doStartTag();
/* 3635 */     if (_jspx_th_awolf_005fparam_005f14.doEndTag() == 5) {
/* 3636 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f14);
/* 3637 */       return true;
/*      */     }
/* 3639 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f14);
/* 3640 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f15(JspTag _jspx_th_awolf_005fmap_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3645 */     PageContext pageContext = _jspx_page_context;
/* 3646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3648 */     AMParam _jspx_th_awolf_005fparam_005f15 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3649 */     _jspx_th_awolf_005fparam_005f15.setPageContext(_jspx_page_context);
/* 3650 */     _jspx_th_awolf_005fparam_005f15.setParent((Tag)_jspx_th_awolf_005fmap_005f3);
/*      */     
/* 3652 */     _jspx_th_awolf_005fparam_005f15.setName("3");
/*      */     
/* 3654 */     _jspx_th_awolf_005fparam_005f15.setValue("#589037");
/* 3655 */     int _jspx_eval_awolf_005fparam_005f15 = _jspx_th_awolf_005fparam_005f15.doStartTag();
/* 3656 */     if (_jspx_th_awolf_005fparam_005f15.doEndTag() == 5) {
/* 3657 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f15);
/* 3658 */       return true;
/*      */     }
/* 3660 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f15);
/* 3661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3666 */     PageContext pageContext = _jspx_page_context;
/* 3667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3669 */     BarChart _jspx_th_awolf_005fbarchart_005f4 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3670 */     _jspx_th_awolf_005fbarchart_005f4.setPageContext(_jspx_page_context);
/* 3671 */     _jspx_th_awolf_005fbarchart_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3673 */     _jspx_th_awolf_005fbarchart_005f4.setDataSetProducer("wlsGraph");
/*      */     
/* 3675 */     _jspx_th_awolf_005fbarchart_005f4.setWidth("600");
/*      */     
/* 3677 */     _jspx_th_awolf_005fbarchart_005f4.setHeight("280");
/*      */     
/* 3679 */     _jspx_th_awolf_005fbarchart_005f4.setLegend("false");
/*      */     
/* 3681 */     _jspx_th_awolf_005fbarchart_005f4.setXaxisLabel("Pool");
/*      */     
/* 3683 */     _jspx_th_awolf_005fbarchart_005f4.setYaxisLabel("");
/* 3684 */     int _jspx_eval_awolf_005fbarchart_005f4 = _jspx_th_awolf_005fbarchart_005f4.doStartTag();
/* 3685 */     if (_jspx_eval_awolf_005fbarchart_005f4 != 0) {
/* 3686 */       if (_jspx_eval_awolf_005fbarchart_005f4 != 1) {
/* 3687 */         out = _jspx_page_context.pushBody();
/* 3688 */         _jspx_th_awolf_005fbarchart_005f4.setBodyContent((BodyContent)out);
/* 3689 */         _jspx_th_awolf_005fbarchart_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3692 */         out.write("\n                                ");
/* 3693 */         if (_jspx_meth_awolf_005fmap_005f4(_jspx_th_awolf_005fbarchart_005f4, _jspx_page_context))
/* 3694 */           return true;
/* 3695 */         out.write("\n                            ");
/* 3696 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f4.doAfterBody();
/* 3697 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3700 */       if (_jspx_eval_awolf_005fbarchart_005f4 != 1) {
/* 3701 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3704 */     if (_jspx_th_awolf_005fbarchart_005f4.doEndTag() == 5) {
/* 3705 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f4);
/* 3706 */       return true;
/*      */     }
/* 3708 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f4);
/* 3709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f4(JspTag _jspx_th_awolf_005fbarchart_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3714 */     PageContext pageContext = _jspx_page_context;
/* 3715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3717 */     Property _jspx_th_awolf_005fmap_005f4 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3718 */     _jspx_th_awolf_005fmap_005f4.setPageContext(_jspx_page_context);
/* 3719 */     _jspx_th_awolf_005fmap_005f4.setParent((Tag)_jspx_th_awolf_005fbarchart_005f4);
/*      */     
/* 3721 */     _jspx_th_awolf_005fmap_005f4.setId("color");
/* 3722 */     int _jspx_eval_awolf_005fmap_005f4 = _jspx_th_awolf_005fmap_005f4.doStartTag();
/* 3723 */     if (_jspx_eval_awolf_005fmap_005f4 != 0) {
/* 3724 */       if (_jspx_eval_awolf_005fmap_005f4 != 1) {
/* 3725 */         out = _jspx_page_context.pushBody();
/* 3726 */         _jspx_th_awolf_005fmap_005f4.setBodyContent((BodyContent)out);
/* 3727 */         _jspx_th_awolf_005fmap_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3730 */         out.write("\n                                    ");
/* 3731 */         if (_jspx_meth_awolf_005fparam_005f16(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 3732 */           return true;
/* 3733 */         out.write("\n                                    ");
/* 3734 */         if (_jspx_meth_awolf_005fparam_005f17(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 3735 */           return true;
/* 3736 */         out.write("\n                                    ");
/* 3737 */         if (_jspx_meth_awolf_005fparam_005f18(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 3738 */           return true;
/* 3739 */         out.write("\n                                    ");
/* 3740 */         if (_jspx_meth_awolf_005fparam_005f19(_jspx_th_awolf_005fmap_005f4, _jspx_page_context))
/* 3741 */           return true;
/* 3742 */         out.write("\n                                ");
/* 3743 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f4.doAfterBody();
/* 3744 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3747 */       if (_jspx_eval_awolf_005fmap_005f4 != 1) {
/* 3748 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3751 */     if (_jspx_th_awolf_005fmap_005f4.doEndTag() == 5) {
/* 3752 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f4);
/* 3753 */       return true;
/*      */     }
/* 3755 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f4);
/* 3756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f16(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3761 */     PageContext pageContext = _jspx_page_context;
/* 3762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3764 */     AMParam _jspx_th_awolf_005fparam_005f16 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3765 */     _jspx_th_awolf_005fparam_005f16.setPageContext(_jspx_page_context);
/* 3766 */     _jspx_th_awolf_005fparam_005f16.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 3768 */     _jspx_th_awolf_005fparam_005f16.setName("0");
/*      */     
/* 3770 */     _jspx_th_awolf_005fparam_005f16.setValue("#C6454A");
/* 3771 */     int _jspx_eval_awolf_005fparam_005f16 = _jspx_th_awolf_005fparam_005f16.doStartTag();
/* 3772 */     if (_jspx_th_awolf_005fparam_005f16.doEndTag() == 5) {
/* 3773 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f16);
/* 3774 */       return true;
/*      */     }
/* 3776 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f16);
/* 3777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f17(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3782 */     PageContext pageContext = _jspx_page_context;
/* 3783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3785 */     AMParam _jspx_th_awolf_005fparam_005f17 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3786 */     _jspx_th_awolf_005fparam_005f17.setPageContext(_jspx_page_context);
/* 3787 */     _jspx_th_awolf_005fparam_005f17.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 3789 */     _jspx_th_awolf_005fparam_005f17.setName("1");
/*      */     
/* 3791 */     _jspx_th_awolf_005fparam_005f17.setValue("#00FF00");
/* 3792 */     int _jspx_eval_awolf_005fparam_005f17 = _jspx_th_awolf_005fparam_005f17.doStartTag();
/* 3793 */     if (_jspx_th_awolf_005fparam_005f17.doEndTag() == 5) {
/* 3794 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f17);
/* 3795 */       return true;
/*      */     }
/* 3797 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f17);
/* 3798 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f18(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3803 */     PageContext pageContext = _jspx_page_context;
/* 3804 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3806 */     AMParam _jspx_th_awolf_005fparam_005f18 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3807 */     _jspx_th_awolf_005fparam_005f18.setPageContext(_jspx_page_context);
/* 3808 */     _jspx_th_awolf_005fparam_005f18.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 3810 */     _jspx_th_awolf_005fparam_005f18.setName("2");
/*      */     
/* 3812 */     _jspx_th_awolf_005fparam_005f18.setValue("#FE7301");
/* 3813 */     int _jspx_eval_awolf_005fparam_005f18 = _jspx_th_awolf_005fparam_005f18.doStartTag();
/* 3814 */     if (_jspx_th_awolf_005fparam_005f18.doEndTag() == 5) {
/* 3815 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f18);
/* 3816 */       return true;
/*      */     }
/* 3818 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f18);
/* 3819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f19(JspTag _jspx_th_awolf_005fmap_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3824 */     PageContext pageContext = _jspx_page_context;
/* 3825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3827 */     AMParam _jspx_th_awolf_005fparam_005f19 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3828 */     _jspx_th_awolf_005fparam_005f19.setPageContext(_jspx_page_context);
/* 3829 */     _jspx_th_awolf_005fparam_005f19.setParent((Tag)_jspx_th_awolf_005fmap_005f4);
/*      */     
/* 3831 */     _jspx_th_awolf_005fparam_005f19.setName("3");
/*      */     
/* 3833 */     _jspx_th_awolf_005fparam_005f19.setValue("#589037");
/* 3834 */     int _jspx_eval_awolf_005fparam_005f19 = _jspx_th_awolf_005fparam_005f19.doStartTag();
/* 3835 */     if (_jspx_th_awolf_005fparam_005f19.doEndTag() == 5) {
/* 3836 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f19);
/* 3837 */       return true;
/*      */     }
/* 3839 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f19);
/* 3840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fbarchart_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3845 */     PageContext pageContext = _jspx_page_context;
/* 3846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3848 */     BarChart _jspx_th_awolf_005fbarchart_005f5 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 3849 */     _jspx_th_awolf_005fbarchart_005f5.setPageContext(_jspx_page_context);
/* 3850 */     _jspx_th_awolf_005fbarchart_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3852 */     _jspx_th_awolf_005fbarchart_005f5.setDataSetProducer("wlsGraph");
/*      */     
/* 3854 */     _jspx_th_awolf_005fbarchart_005f5.setWidth("600");
/*      */     
/* 3856 */     _jspx_th_awolf_005fbarchart_005f5.setHeight("280");
/*      */     
/* 3858 */     _jspx_th_awolf_005fbarchart_005f5.setLegend("false");
/*      */     
/* 3860 */     _jspx_th_awolf_005fbarchart_005f5.setXaxisLabel("Pool");
/*      */     
/* 3862 */     _jspx_th_awolf_005fbarchart_005f5.setYaxisLabel("");
/* 3863 */     int _jspx_eval_awolf_005fbarchart_005f5 = _jspx_th_awolf_005fbarchart_005f5.doStartTag();
/* 3864 */     if (_jspx_eval_awolf_005fbarchart_005f5 != 0) {
/* 3865 */       if (_jspx_eval_awolf_005fbarchart_005f5 != 1) {
/* 3866 */         out = _jspx_page_context.pushBody();
/* 3867 */         _jspx_th_awolf_005fbarchart_005f5.setBodyContent((BodyContent)out);
/* 3868 */         _jspx_th_awolf_005fbarchart_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3871 */         out.write("\n                                ");
/* 3872 */         if (_jspx_meth_awolf_005fmap_005f5(_jspx_th_awolf_005fbarchart_005f5, _jspx_page_context))
/* 3873 */           return true;
/* 3874 */         out.write("\n                            ");
/* 3875 */         int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f5.doAfterBody();
/* 3876 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3879 */       if (_jspx_eval_awolf_005fbarchart_005f5 != 1) {
/* 3880 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3883 */     if (_jspx_th_awolf_005fbarchart_005f5.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f5);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f5);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f5(JspTag _jspx_th_awolf_005fbarchart_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     Property _jspx_th_awolf_005fmap_005f5 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3897 */     _jspx_th_awolf_005fmap_005f5.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_awolf_005fmap_005f5.setParent((Tag)_jspx_th_awolf_005fbarchart_005f5);
/*      */     
/* 3900 */     _jspx_th_awolf_005fmap_005f5.setId("color");
/* 3901 */     int _jspx_eval_awolf_005fmap_005f5 = _jspx_th_awolf_005fmap_005f5.doStartTag();
/* 3902 */     if (_jspx_eval_awolf_005fmap_005f5 != 0) {
/* 3903 */       if (_jspx_eval_awolf_005fmap_005f5 != 1) {
/* 3904 */         out = _jspx_page_context.pushBody();
/* 3905 */         _jspx_th_awolf_005fmap_005f5.setBodyContent((BodyContent)out);
/* 3906 */         _jspx_th_awolf_005fmap_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3909 */         out.write("\n                                    ");
/* 3910 */         if (_jspx_meth_awolf_005fparam_005f20(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 3911 */           return true;
/* 3912 */         out.write("\n                                    ");
/* 3913 */         if (_jspx_meth_awolf_005fparam_005f21(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 3914 */           return true;
/* 3915 */         out.write("\n                                    ");
/* 3916 */         if (_jspx_meth_awolf_005fparam_005f22(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 3917 */           return true;
/* 3918 */         out.write("\n                                    ");
/* 3919 */         if (_jspx_meth_awolf_005fparam_005f23(_jspx_th_awolf_005fmap_005f5, _jspx_page_context))
/* 3920 */           return true;
/* 3921 */         out.write("\n                                ");
/* 3922 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f5.doAfterBody();
/* 3923 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3926 */       if (_jspx_eval_awolf_005fmap_005f5 != 1) {
/* 3927 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3930 */     if (_jspx_th_awolf_005fmap_005f5.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f5);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f5);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f20(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     AMParam _jspx_th_awolf_005fparam_005f20 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3944 */     _jspx_th_awolf_005fparam_005f20.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_awolf_005fparam_005f20.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 3947 */     _jspx_th_awolf_005fparam_005f20.setName("0");
/*      */     
/* 3949 */     _jspx_th_awolf_005fparam_005f20.setValue("#C6454A");
/* 3950 */     int _jspx_eval_awolf_005fparam_005f20 = _jspx_th_awolf_005fparam_005f20.doStartTag();
/* 3951 */     if (_jspx_th_awolf_005fparam_005f20.doEndTag() == 5) {
/* 3952 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f20);
/* 3953 */       return true;
/*      */     }
/* 3955 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f20);
/* 3956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f21(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3961 */     PageContext pageContext = _jspx_page_context;
/* 3962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3964 */     AMParam _jspx_th_awolf_005fparam_005f21 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3965 */     _jspx_th_awolf_005fparam_005f21.setPageContext(_jspx_page_context);
/* 3966 */     _jspx_th_awolf_005fparam_005f21.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 3968 */     _jspx_th_awolf_005fparam_005f21.setName("1");
/*      */     
/* 3970 */     _jspx_th_awolf_005fparam_005f21.setValue("#00FF00");
/* 3971 */     int _jspx_eval_awolf_005fparam_005f21 = _jspx_th_awolf_005fparam_005f21.doStartTag();
/* 3972 */     if (_jspx_th_awolf_005fparam_005f21.doEndTag() == 5) {
/* 3973 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f21);
/* 3974 */       return true;
/*      */     }
/* 3976 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f21);
/* 3977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f22(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3982 */     PageContext pageContext = _jspx_page_context;
/* 3983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3985 */     AMParam _jspx_th_awolf_005fparam_005f22 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3986 */     _jspx_th_awolf_005fparam_005f22.setPageContext(_jspx_page_context);
/* 3987 */     _jspx_th_awolf_005fparam_005f22.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 3989 */     _jspx_th_awolf_005fparam_005f22.setName("2");
/*      */     
/* 3991 */     _jspx_th_awolf_005fparam_005f22.setValue("#FE7301");
/* 3992 */     int _jspx_eval_awolf_005fparam_005f22 = _jspx_th_awolf_005fparam_005f22.doStartTag();
/* 3993 */     if (_jspx_th_awolf_005fparam_005f22.doEndTag() == 5) {
/* 3994 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f22);
/* 3995 */       return true;
/*      */     }
/* 3997 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f22);
/* 3998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f23(JspTag _jspx_th_awolf_005fmap_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4003 */     PageContext pageContext = _jspx_page_context;
/* 4004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4006 */     AMParam _jspx_th_awolf_005fparam_005f23 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4007 */     _jspx_th_awolf_005fparam_005f23.setPageContext(_jspx_page_context);
/* 4008 */     _jspx_th_awolf_005fparam_005f23.setParent((Tag)_jspx_th_awolf_005fmap_005f5);
/*      */     
/* 4010 */     _jspx_th_awolf_005fparam_005f23.setName("3");
/*      */     
/* 4012 */     _jspx_th_awolf_005fparam_005f23.setValue("#589037");
/* 4013 */     int _jspx_eval_awolf_005fparam_005f23 = _jspx_th_awolf_005fparam_005f23.doStartTag();
/* 4014 */     if (_jspx_th_awolf_005fparam_005f23.doEndTag() == 5) {
/* 4015 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f23);
/* 4016 */       return true;
/*      */     }
/* 4018 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f23);
/* 4019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4024 */     PageContext pageContext = _jspx_page_context;
/* 4025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4027 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4028 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4029 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 4031 */     _jspx_th_c_005fset_005f0.setVar("datatype");
/*      */     
/* 4033 */     _jspx_th_c_005fset_005f0.setValue("3");
/*      */     
/* 4035 */     _jspx_th_c_005fset_005f0.setScope("session");
/* 4036 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4037 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4038 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4039 */       return true;
/*      */     }
/* 4041 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4042 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\pool_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */