/*     */ package org.apache.jsp.jsp.formpages;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class Error404_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  52 */     HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html;charset=UTF-8");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("<!DOCTYPE html>\n");
/*  73 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  74 */       out.write("\n\n\n\n\n\n\n");
/*  75 */       out.write(10);
/*     */       
/*  77 */       String faviconHref = "/favicon.ico";
/*  78 */       String productName = OEMUtil.getOEMString("product.name");
/*  79 */       if (Constants.isIt360)
/*     */       {
/*  81 */         productName = OEMUtil.getOEMString("rebrand.product.name");
/*     */       }
/*  83 */       if (Constants.isIt360)
/*     */       {
/*  85 */         faviconHref = "/favicon.ico?" + System.currentTimeMillis();
/*  86 */         String rebrandedId = "-1";
/*  87 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*     */         {
/*  89 */           Object tmpObj1 = request.getSession().getAttribute("rebrandId");
/*  90 */           if (tmpObj1 != null)
/*     */           {
/*  92 */             rebrandedId = tmpObj1.toString();
/*     */           }
/*     */           else {
/*     */             try
/*     */             {
/*  97 */               String usrName = request.getUserPrincipal().getName();
/*     */               
/*     */ 
/*     */ 
/* 101 */               String className1 = "com.adventnet.console.common.util.ConsoleRemoteUtil";
/* 102 */               String methodName1 = "getUserInfo";
/* 103 */               Class conClass1 = Class.forName(className1);
/* 104 */               String className2 = "com.adventnet.console.UserInfo";
/* 105 */               String methodName2 = "getGroupName";
/* 106 */               Class conClass2 = Class.forName(className2);
/* 107 */               Class[] parameters = { String.class };
/* 108 */               Method myMethod = conClass1.getMethod(methodName1, parameters);
/* 109 */               Object[] arguments = { usrName };
/* 110 */               Object returnFromMethod1 = myMethod.invoke(null, arguments);
/* 111 */               Method it360Method = conClass2.getMethod(methodName2, new Class[0]);
/* 112 */               Object returnFromMethod2 = it360Method.invoke(returnFromMethod1, null);
/* 113 */               String rName = (String)returnFromMethod2;
/* 114 */               rebrandedId = CustomerManagementAPI.getRebrandIdForUser(usrName, rName);
/*     */             } catch (Exception e) {}
/*     */           }
/*     */         }
/* 118 */         if (!rebrandedId.equals("-1"))
/*     */         {
/* 120 */           faviconHref = "/custom/customimages/Custom_FaviconLogo_" + rebrandedId + ".ico?" + System.currentTimeMillis();
/*     */         }
/*     */       }
/*     */       
/* 124 */       out.write(10);
/* 125 */       out.write("\n<html>\n<head>\n<title>");
/* 126 */       out.print(productName);
/* 127 */       out.write("</title>\n\n<link REL=\"SHORTCUT ICON\" HREF=\"");
/* 128 */       out.print(faviconHref);
/* 129 */       out.write("\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n\n<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n<div class=\"error-page\">\n\t<p>\n\t\t<img border=\"0\" src=\"/images/am_logo.png\">\n   \t</p>\n\t<h3 class=\"error-txt\">");
/* 130 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/* 132 */       out.write("</h3>");
/* 133 */       out.write("\n \t<p class=\"error-txt2\">");
/* 134 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*     */         return;
/* 136 */       out.write("<br></p>\n</div>\n</body>\n</html>\n\n  \n");
/*     */     } catch (Throwable t) {
/* 138 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 139 */         out = _jspx_out;
/* 140 */         if ((out != null) && (out.getBufferSize() != 0))
/* 141 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 142 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 145 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 151 */     PageContext pageContext = _jspx_page_context;
/* 152 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 154 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 155 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 156 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 158 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.pagedoesnt.exist");
/* 159 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 160 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 161 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 162 */       return true;
/*     */     }
/* 164 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 170 */     PageContext pageContext = _jspx_page_context;
/* 171 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 173 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 174 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 175 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*     */     
/* 177 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.mistyped.msg");
/* 178 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 179 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 180 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 181 */       return true;
/*     */     }
/* 183 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 184 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\Error404_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */