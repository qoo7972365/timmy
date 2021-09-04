/*     */ package org.apache.jsp.jsp.sap;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class listccmsmonitorsets_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  30 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  34 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html;charset=UTF-8");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("<!-- $Id$ -->\n\n\n\n<link href=\"/images/");
/*  68 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  70 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"/template/appmanager.js\" ></SCRIPT>\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"/template/validation.js\" ></SCRIPT>\n\n<script>\nvar http1;\nfunction loadCCMS()\n{\n\tvar host='localhost';\n\tvar logonclient='000';\n\tvar systemnumber='00';\n\tvar language='EN';\n\tvar username='bcuser';\n\tvar password='minisap';\n\tvar monitorset='SAP CCMS Monitor Templates';\n\tvar routerString = '';\n\tfor(i=0; i<window.opener.document.AMActionForm.elements.length; i++)\n\t{\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"host\")\n\t\t{\n\t\t\thost=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"logonClient\")\n\t\t{\n\t\t\tlogonclient=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"systemNumber\")\n\t\t{\n\t\t\tsystemnumber=window.opener.document.AMActionForm.elements[i].value;\n");
/*  71 */       out.write("\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"language\")\n\t\t{\n\t\t\tlanguage=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"username\")\n\t\t{\n\t\t\tusername=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"password\")\n\t\t{\n\t\t\tpassword=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"version\")\n\t\t{\n\t\t\tmonitorset=window.opener.document.AMActionForm.elements[i].value;\n\t\t}\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"usedRouterString\" && window.opener.document.AMActionForm.elements[i].checked)\n\t\t{\n\t\t\trouterString=window.opener.document.AMActionForm.elements[i+1].value;\n\t\t}\n\t}\n\tvar url = '/sap.do?method=showCCMSMonitorSets&host='+escape(host)+'&systemnumber='+systemnumber+'&logonclient='+logonclient+'&language='+escape(language)+'&username='+escape(username)+'&password='+escape(password);\t//NO I18N\n");
/*  72 */       out.write("\tif(routerString != \"\"){\n\t\turl=url+'&routerString='+escape(routerString);\t\t//NO I18N\n\t}\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status== 200){document.getElementById(\"ccms_cogwheel\").innerHTML =\"&nbsp;\",document.getElementById(\"ccms_monitorsets\").innerHTML = http1.responseText;}');\n\thttp1.open(\"GET\",url,true);\n\thttp1.send(null);\n}\n\nfunction setCCMSMonitorSet(monitorsetname)\n{\n\tfor(i=0; i<window.opener.document.AMActionForm.elements.length; i++)\n\t{\n\t\tif(window.opener.document.AMActionForm.elements[i].name==\"version\")\n\t\t{\n\t\t\twindow.opener.document.AMActionForm.elements[i].value=trimAll(monitorsetname);\n\t\t\twindow.opener.document.AMActionForm.elements[i].style.size=monitorsetname.length;\n\t\t\tif(monitorsetname.length>=12)\n\t\t\t{\n\t\t\t\twindow.opener.document.AMActionForm.elements[i].style.width=monitorsetname.length+'%';\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\twindow.opener.document.AMActionForm.elements[i].style.width='12%';\n\t\t\t}\n\t\t\twindow.close();\n\t\t}\n\t}\n}\n</script>\n\n<body onload=\"loadCCMS()\">\n");
/*  73 */       out.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" bgcolor='FFFFFF'>\n<tr>\n<td colspan=2 class=\"bodytext\">");
/*  74 */       out.print(FormatUtil.getString("am.webclient.ccms.newmonitor.choosemonitorset"));
/*  75 */       out.write("</td>\n</tr>\n<tr><td colspan=\"2\">&nbsp;</td></tr>\n<tr>\n<td><img border=\"0\" src=\"/images/icon_alert_unacknowleged.gif\">&nbsp;</td>\n<td Style=\"font-family: Arial, Helvetica, sans-serif; font-size: 10px; COLOR: #FF0000; TEXT-DECORATION:none\"> ");
/*  76 */       out.print(FormatUtil.getString("am.webclient.ccms.newmonitor.warning"));
/*  77 */       out.write("</td>\n</tr>\n</table>\n<div id=\"ccms_cogwheel\" style=\"display: inline;\"><img border=\"0\" src=\"/images/icon_cogwheel.gif\"></div>\n<div id=\"ccms_monitorsets\" style=\"display: inline;\">&nbsp;</div>\n</body>\n");
/*     */     } catch (Throwable t) {
/*  79 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  80 */         out = _jspx_out;
/*  81 */         if ((out != null) && (out.getBufferSize() != 0))
/*  82 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  83 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/*  86 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/*  92 */     PageContext pageContext = _jspx_page_context;
/*  93 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/*  95 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  96 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  97 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/*  99 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 101 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 102 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 103 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 104 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 105 */       return true;
/*     */     }
/* 107 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 108 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sap\listccmsmonitorsets_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */