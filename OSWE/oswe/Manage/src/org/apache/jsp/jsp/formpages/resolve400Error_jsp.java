/*    */ package org.apache.jsp.jsp.formpages;
/*    */ 
/*    */ import com.manageengine.appmanager.plugin.PluginUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class resolve400Error_jsp extends HttpJspBase implements JspSourceDependent
/*    */ {
/* 20 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 29 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 34 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 44 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 47 */     JspWriter out = null;
/* 48 */     Object page = this;
/* 49 */     JspWriter _jspx_out = null;
/* 50 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 54 */       response.setContentType("text/html;charset=UTF-8");
/* 55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 57 */       _jspx_page_context = pageContext;
/* 58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 59 */       ServletConfig config = pageContext.getServletConfig();
/* 60 */       session = pageContext.getSession();
/* 61 */       out = pageContext.getOut();
/* 62 */       _jspx_out = out;
/*    */       
/* 64 */       out.write("<!DOCTYPE html>\n");
/* 65 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 66 */       out.write("\n\n \n \n \n<script type=\"text/javascript\">\nif (true){\n if (document.all) //IE4+ specific code\n {\n   document.cookie=\"testcookie\"\n   cookieEnabled=(document.cookie.indexOf(\"testcookie\")!=-1)? true : false\n }\n else\n {\n\tSet_Cookie('testcookie','',0);\t//NO I18N\n\tvar tempCookie = Get_Cookie('testcookie');\n\tif(tempCookie !=null)\n\t{\n      cookieEnabled=true;\n\t}\n\telse\n\t{\n\t  cookieEnabled=false;\n\t}\n }\n}\n\nif (!cookieEnabled) \n{\n\tlocation.href=\"/html/EnableCookies.html\";\t\n}\nelse\n{\n\t");
/* 67 */       if (!PluginUtil.isPlugin()) {
/* 68 */         out.write("\n\t\tlocation.href = \"/webclient/common/jsp/home.jsp\";\t\n\t");
/*    */       }
/*    */       else {
/* 71 */         out.write("\n\t\tlocation.href = \"/showresource.do?group=All&method=showResourceTypes&monitor_viewtype=categoryview\";\n\t");
/*    */       }
/* 73 */       out.write("\n}\n\nfunction Set_Cookie(name,value,expires,path,domain,secure) {\n    document.cookie = name + \"=\" +escape(value) +\t\t//NO I18N\n        ( (expires) ? \";expires=\" + expires.toGMTString() : \"\") +\t\t//NO I18N\n        ( (path) ? \";path=\" + path : \"\") +\t\t//NO I18N\n        ( (domain) ? \";domain=\" + domain : \"\") +\t\t//NO I18N\n        ( (secure) ? \";secure\" : \"\");\t\t//NO I18N\n}\n\nfunction Get_Cookie(name) {\n    var start = document.cookie.indexOf(name+\"=\");\n    var len = start+name.length+1;\n    if ((!start) && (name != document.cookie.substring(0,name.length))) return null;\n    if (start == -1) return null;\n    var end = document.cookie.indexOf(\";\",len);\n    if (end == -1) end = document.cookie.length;\n    return unescape(document.cookie.substring(len,end));\n}\n</script>\n\n");
/*    */     } catch (Throwable t) {
/* 75 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 76 */         out = _jspx_out;
/* 77 */         if ((out != null) && (out.getBufferSize() != 0))
/* 78 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 79 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 82 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\resolve400Error_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */