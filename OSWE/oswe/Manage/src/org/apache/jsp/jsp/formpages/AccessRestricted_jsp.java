/*     */ package org.apache.jsp.jsp.formpages;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.ResultSet;
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
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AccessRestricted_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  26 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  50 */     HttpSession session = null;
/*     */     
/*     */ 
/*  53 */     JspWriter out = null;
/*  54 */     Object page = this;
/*  55 */     JspWriter _jspx_out = null;
/*  56 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  60 */       response.setContentType("text/html;charset=UTF-8");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<meta http-equiv=\"Content-Type\" content=\"UTF-8\">\n<!--$Id$-->\n\n\n\n\n\n\n");
/*  71 */       out.write(10);
/*     */       
/*  73 */       String faviconHref = "/favicon.ico";
/*  74 */       String productName = OEMUtil.getOEMString("product.name");
/*  75 */       if (Constants.isIt360)
/*     */       {
/*  77 */         productName = OEMUtil.getOEMString("rebrand.product.name");
/*     */       }
/*  79 */       if (Constants.isIt360)
/*     */       {
/*  81 */         faviconHref = "/favicon.ico?" + System.currentTimeMillis();
/*  82 */         String rebrandedId = "-1";
/*  83 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*     */         {
/*  85 */           Object tmpObj1 = request.getSession().getAttribute("rebrandId");
/*  86 */           if (tmpObj1 != null)
/*     */           {
/*  88 */             rebrandedId = tmpObj1.toString();
/*     */           }
/*     */           else {
/*     */             try
/*     */             {
/*  93 */               String usrName = request.getUserPrincipal().getName();
/*     */               
/*     */ 
/*     */ 
/*  97 */               String className1 = "com.adventnet.console.common.util.ConsoleRemoteUtil";
/*  98 */               String methodName1 = "getUserInfo";
/*  99 */               Class conClass1 = Class.forName(className1);
/* 100 */               String className2 = "com.adventnet.console.UserInfo";
/* 101 */               String methodName2 = "getGroupName";
/* 102 */               Class conClass2 = Class.forName(className2);
/* 103 */               Class[] parameters = { String.class };
/* 104 */               Method myMethod = conClass1.getMethod(methodName1, parameters);
/* 105 */               Object[] arguments = { usrName };
/* 106 */               Object returnFromMethod1 = myMethod.invoke(null, arguments);
/* 107 */               Method it360Method = conClass2.getMethod(methodName2, new Class[0]);
/* 108 */               Object returnFromMethod2 = it360Method.invoke(returnFromMethod1, null);
/* 109 */               String rName = (String)returnFromMethod2;
/* 110 */               rebrandedId = CustomerManagementAPI.getRebrandIdForUser(usrName, rName);
/*     */             } catch (Exception e) {}
/*     */           }
/*     */         }
/* 114 */         if (!rebrandedId.equals("-1"))
/*     */         {
/* 116 */           faviconHref = "/custom/customimages/Custom_FaviconLogo_" + rebrandedId + ".ico?" + System.currentTimeMillis();
/*     */         }
/*     */       }
/*     */       
/* 120 */       out.write(10);
/* 121 */       out.write("\n<html>\n<head>\n<title>");
/* 122 */       out.print(productName);
/* 123 */       out.write("</title>\n\n<link REL=\"SHORTCUT ICON\" HREF=\"");
/* 124 */       out.print(faviconHref);
/* 125 */       out.write(34);
/* 126 */       out.write(62);
/* 127 */       out.write(10);
/* 128 */       if (Constants.isIt360)
/*     */       {
/* 130 */         out.write("\n\t<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       }
/* 132 */       out.write("\n</head>\n\n<body style=\"background-color:#fff;\">\n\n<style type=\"text/css\">\n\t#container-error\n\t{\n\t\tborder:1px solid #c1c1c1;\n\t\tbackground: #fff; font:11px Arial, Helvetica, sans-serif; width:90%; margin:80px;\n\t \t\n\t}\n\n\t#header-error\n\t{\n\t\tbackground: #ededed; line-height:18px;\n\t\tpadding: 15px; color:#000; font-size:8px;\n\t}\n\n\t#header-error h1\n\t{\n\t\tmargin: 0;  color:#000;\n\t\tfont-size:14px;\n\t}\n\n\t#content-error\n\t{\n\t\tclear: left; background-color:#fff9f9;\n\t\tpadding: 20px; height:170px;\n\t}\n\t#content-error1\n\t{\n\t\tclear: left; background-color:#fff9f9;\n\t\tpadding-left: 20px;\t\t\n\t\tpadding-right: 2px;\n\t\tpadding-top: 2px;\n\t\t height:200px;\n\n\t}\n\t#content-error h2\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 100%;\n\t\tmargin: 0 0 .5em;\n\t}\n\t#content-error p\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\tmargin: 3em 0 .5em;\n\t}\n\t\t#content-error1 p\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\t\n\t}\t#content-error1 li\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\tpadding:2px;\n\n\t}\n\t#footer-error\n\t{\n\t\tbackground: #ededed;\n\t\ttext-align: right;\n\t\tpadding: 10px;\n\t\theight: 1%; font-size:10px;\n");
/* 133 */       out.write("\t}\n</style>\n");
/*     */       
/* 135 */       String query = "select emailid from AM_UserPasswordTable where username='admin' ";
/* 136 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 137 */       String emailid = null;
/* 138 */       if (rs.next())
/*     */       {
/* 140 */         emailid = rs.getString(1);
/*     */       }
/* 142 */       AMConnectionPool.closeStatement(rs);
/*     */       
/*     */ 
/* 145 */       out.write("\n\n<div id=\"container-error\">\n\t<div id=\"header-error\">\n\t\t<h1>\n\t\t\t");
/* 146 */       out.print(productName);
/* 147 */       out.write("\n\t\t</h1>\n\t</div>\n\t");
/* 148 */       if ((request.getParameter("EnableCookiesInIE") != null) && (request.getParameter("EnableCookiesInIE").equals("true"))) {
/* 149 */         out.write("\n\t<div id=\"content-error1\">\n\t\t<p>\n\t\t<img src=\"/images/icon_cross.gif\" style=\"position: relative; top: 18px; right: 9px;\">\t\t\n\t\t\t");
/* 150 */         out.print(FormatUtil.getString("am.webclient.userauthorization.nonstandard.hostname"));
/* 151 */         out.write("\t\n\t</div>\n\t");
/*     */       } else {
/* 153 */         out.write("\n\t<div id=\"content-error\" align=\"center\">\n\t\t<p>\n\t\t\t<img src=\"/images/icon_cross.gif\" style=\"position: relative; top: 18px; right: 9px;\">\n\t\t\t");
/*     */         
/* 155 */         if (emailid == null) {
/* 156 */           out.write(" \n\t\t\t\t\t \t");
/* 157 */           out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 158 */           out.write("\n\t\t\t");
/*     */ 
/*     */         }
/* 161 */         else if (emailid.length() == 0)
/*     */         {
/* 163 */           out.write(9);
/* 164 */           out.write(9);
/* 165 */           out.write(9);
/* 166 */           out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/* 167 */           out.write("\n\t\t\t");
/*     */         }
/*     */         else
/*     */         {
/* 171 */           out.write(9);
/* 172 */           out.write(9);
/* 173 */           out.write(9);
/* 174 */           out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised.email", new String[] { emailid }));
/* 175 */           out.write("\n\t\t\t");
/*     */         }
/*     */         
/*     */ 
/* 179 */         out.write("\n\t\t\t<a href=\"javascript: history.go(-1)\" class=\"staticlinks\" >");
/* 180 */         out.print(FormatUtil.getString("am.webclient.goback.text"));
/* 181 */         out.write("</a>\n\t\t</p>\n\t</div>\n\t");
/*     */       }
/* 183 */       out.write("\n\t<div id=\"footer-error\">\n\t\t&copy; ");
/* 184 */       out.print(OEMUtil.getOEMString("company.currentyear"));
/* 185 */       out.write(32);
/* 186 */       out.print(OEMUtil.getOEMString("company.name"));
/* 187 */       out.write(9);
/* 188 */       out.write("\n\t</div>\n</div>\n\n</body>\n</html>\n\n\n");
/*     */     } catch (Throwable t) {
/* 190 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 191 */         out = _jspx_out;
/* 192 */         if ((out != null) && (out.getBufferSize() != 0))
/* 193 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 194 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 197 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\AccessRestricted_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */