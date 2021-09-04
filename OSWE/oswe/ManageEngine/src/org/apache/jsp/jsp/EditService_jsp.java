/*     */ package org.apache.jsp.jsp;
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
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ 
/*     */ public final class EditService_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  41 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  48 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  51 */     JspWriter out = null;
/*  52 */     Object page = this;
/*  53 */     JspWriter _jspx_out = null;
/*  54 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  58 */       response.setContentType("text/html;charset=UTF-8");
/*  59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  61 */       _jspx_page_context = pageContext;
/*  62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  63 */       ServletConfig config = pageContext.getServletConfig();
/*  64 */       session = pageContext.getSession();
/*  65 */       out = pageContext.getOut();
/*  66 */       _jspx_out = out;
/*     */       
/*  68 */       out.write("\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\n\tfunction OnValidatePort()\n\t{\n\t\t\t");
/*  69 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  71 */       out.write("\n\t\t port = document.ModifyServiceForm.port.value;\n                var portarray=port.split(\",\");\n                var num=0;\n                while(num < portarray.length)\n                {\n                        var singleport =portarray[num];\n                        num+=1;\n                        if(!isPositiveInteger(singleport))\n                        {\n                                alert('");
/*  72 */       out.print(FormatUtil.getString("am.webclient.portdiscovery.alert.validint"));
/*  73 */       out.write("');\n                                return;\n                        }\n\t\t\t\t\t\telse if(singleport==0)\n                        {\n\t                       \t\talert('");
/*  74 */       out.print(FormatUtil.getString("am.webclient.portdiscovery.alert.nonzero"));
/*  75 */       out.write("');\n                                return;\n                        }\n\n                }\n                document.ModifyServiceForm.submit();\n\t}\n</SCRIPT>\n\t\t\t");
/*     */       
/*  77 */       String type = request.getParameter("type");
/*  78 */       String displayname = request.getParameter("displayname");
/*  79 */       String port = request.getParameter("port");
/*  80 */       String enablediscovery = request.getParameter("enablediscovery");
/*     */       
/*  82 */       out.write("\n\t\t\t<form name=\"ModifyServiceForm\" action=\"/adminAction.do?method=modifyServicePort&type=");
/*  83 */       out.print(type);
/*  84 */       out.write("&enablediscovery=");
/*  85 */       out.print(enablediscovery);
/*  86 */       out.write("\" method=\"POST\" >\n\t\t\t<input type=\"hidden\" name=\"displayname\" value=\"");
/*  87 */       out.print(displayname);
/*  88 */       out.write("\">\n\t\t\t\n\t\t\t\n\t\t\t<table  width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\t\n\t\t\t<tr>\n\t\t\t<td nowrap heigth=\"28\" width=\"35%\" class=\"label-align\">");
/*  89 */       out.print(FormatUtil.getString("am.webclient.networkdiscovery.servicename.txt"));
/*  90 */       out.write("</td>\n\t\t\t<td width=\"5%\" heigth=\"28\" class=\"cenAlign\">:</td>\n\t\t\t<td align=left width=\"60%\" heigth=\"28\" id=\"serviceNameForMod\"> ");
/*  91 */       out.print(displayname);
/*  92 */       out.write("</td>\n\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td nowrap heigth=\"28\" width=\"35%\" class=\"label-align\">");
/*  93 */       out.print(FormatUtil.getString("am.webclient.networkdiscovery.port.txt"));
/*  94 */       out.write("</td>\n\t\t\t<td width=\"5%\" heigth=\"28\" class=\"cenAlign\">:</td>\n\t\t\t<td align=left width=\"60%\" heigth=\"28\"><input type=\"text\" name=\"port\" class=\"formtext medium\" value=");
/*  95 */       out.print(port);
/*  96 */       out.write(" ></td>\t\t\t\n\t\t\t</tr>\n\t\t\t<tr>\n            <td width=\"40%\" colspan=\"2\">&nbsp;</td>\n\t\t\t<td nowrap width=\"60%\">\n\t\t\t\t<input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  97 */       out.print(FormatUtil.getString("am.webclient.common.save.text"));
/*  98 */       out.write("\" onClick=\"OnValidatePort()\"> \n\t\t\t</td>\t\t\t\n\t\t\t</tr>\t\n\t\t\t</table>\n\t\t\t</form>\n");
/*     */     } catch (Throwable t) {
/* 100 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 101 */         out = _jspx_out;
/* 102 */         if ((out != null) && (out.getBufferSize() != 0))
/* 103 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 104 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 107 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 113 */     PageContext pageContext = _jspx_page_context;
/* 114 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 116 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 117 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 118 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 120 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 121 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 122 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 124 */         out.write("\n\t\t\t\talertUser();\n\t\t\t\treturn false;\n\t\t\t");
/* 125 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 126 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 130 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 131 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 132 */       return true;
/*     */     }
/* 134 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditService_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */