/*     */ package org.apache.jsp.jsp.viewlogs;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
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
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class ViewLogFile_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
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
/*  68 */       out.write("<!DOCTYPE html>\n");
/*  69 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  70 */       out.write("\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n<title>");
/*  71 */       out.print(FormatUtil.getString("am.title.viewlogs"));
/*  72 */       out.write(32);
/*  73 */       out.write(45);
/*  74 */       out.write(32);
/*  75 */       out.print(request.getParameter("fileName"));
/*  76 */       out.write("</title> ");
/*  77 */       out.write("\n <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n  <style type=\"text/css\">\n  \ttable{\n  \t\tfont-size: 12px;\n\t\tline-height: 20px;\n  \t}\n    h3.log-head a { \n\t\tfont:bold 14px Arial, Helvetica, sans-serif !important;\n\t\tcolor:#666;\n\t\tpadding:0; \n\t\tmargin:0;\n\t}\n\t</style>\n\t</head>\n    <body>\n      <br/><br/>\n\t  <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tabBtmLine\" align=\"center\" height=\"50\">\n\t\t\t  <tr>\n\t\t\t\t  <td  width=\"89%\" ><span class=\"header2\"><a class=\"footer\" href=\"javascript:window.history.back()\"/>");
/*  78 */       out.print(FormatUtil.getString("am.webclient.support.log.goback"));
/*  79 */       out.write("</a> </span></td>\n\t\t      </tr>\n\t\t\t  <tr height=\"20\"><td>&nbsp;</td></tr>\n\t\t        ");
/*     */       
/*  81 */       String lhome = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath() + File.separator + "logs";
/*     */       
/*  83 */       out.write("\n\t\t    <tr><td><h3>");
/*  84 */       out.print(request.getParameter("fileName"));
/*  85 */       out.write("</h3></td></tr>\n\t</table>\t\n\t    <br/>\n   <table border='0' width=\"90%\" align=\"center\">\n    <tr><td class=\"adminbodytext\">\n     ");
/*     */       
/*  87 */       StringBuffer sbuffer = new StringBuffer();
/*  88 */       String logFile = null;
/*     */       try {
/*  90 */         logFile = request.getParameter("fileName");
/*  91 */         logFile = lhome + logFile;
/*  92 */         BufferedReader inreader = new BufferedReader(new java.io.FileReader(logFile));
/*  93 */         String line = null;
/*  94 */         int check = inreader.read();
/*  95 */         if (check == -1)
/*     */         {
/*  97 */           out.println("File is empty");
/*     */         }
/*  99 */         while ((line = inreader.readLine()) != null)
/*     */         {
/* 101 */           line = line.replaceAll("<", "&lt;");
/* 102 */           sbuffer.append(line + "\n");
/* 103 */           out.println(line + "<br>");
/*     */         }
/* 105 */         inreader.close();
/*     */       }
/*     */       catch (FileNotFoundException e)
/*     */       {
/* 109 */         System.out.println(e.getMessage());
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 113 */         System.out.println(e.getMessage());
/*     */       }
/*     */       catch (NullPointerException e)
/*     */       {
/* 117 */         System.out.println(e.getMessage());
/*     */       }
/*     */       
/* 120 */       out.write("\n    \n    </td><tr></table>\n\t</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 122 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 123 */         out = _jspx_out;
/* 124 */         if ((out != null) && (out.getBufferSize() != 0))
/* 125 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 126 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 129 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\viewlogs\ViewLogFile_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */