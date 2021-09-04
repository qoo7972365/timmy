/*     */ package jsp;
/*     */ 
/*     */ import com.adventnet.nms.util.JnlpFileUpdater;
/*     */ import java.io.Writer;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ 
/*     */ public class WebStart extends HttpJspBase
/*     */ {
/*  18 */   private static boolean _jspx_inited = false;
/*     */   
/*     */   public final void _jspx_init()
/*     */     throws org.apache.jasper.runtime.JspException
/*     */   {}
/*     */   
/*     */   public void _jspService(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws java.io.IOException, ServletException
/*     */   {
/*  26 */     JspFactory localJspFactory = null;
/*  27 */     PageContext localPageContext = null;
/*  28 */     javax.servlet.http.HttpSession localHttpSession = null;
/*  29 */     javax.servlet.ServletContext localServletContext = null;
/*  30 */     ServletConfig localServletConfig = null;
/*  31 */     JspWriter localJspWriter = null;
/*  32 */     WebStart localWebStart = this;
/*  33 */     Object localObject1 = null;
/*     */     try
/*     */     {
/*  36 */       if (!_jspx_inited) {
/*  37 */         synchronized (this) {
/*  38 */           if (!_jspx_inited) {
/*  39 */             _jspx_init();
/*  40 */             _jspx_inited = true;
/*     */           }
/*     */         }
/*     */       }
/*  44 */       localJspFactory = JspFactory.getDefaultFactory();
/*  45 */       paramHttpServletResponse.setContentType("text/html;ISO-8859-1");
/*  46 */       localPageContext = localJspFactory.getPageContext(this, paramHttpServletRequest, paramHttpServletResponse, "", true, 8192, true);
/*     */       
/*     */ 
/*  49 */       localServletContext = localPageContext.getServletContext();
/*  50 */       localServletConfig = localPageContext.getServletConfig();
/*  51 */       localHttpSession = localPageContext.getSession();
/*  52 */       localJspWriter = localPageContext.getOut();
/*     */       
/*     */ 
/*  55 */       localJspWriter.write("\n\n<html>\n<head> \n<title>\n    Checking the presence of Java Web Start in the Local Machine \n</title>\n<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\"../template/nmshtmlui.css\">\n</head>\n\n<body>\n\n\n");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  60 */       ??? = paramHttpServletRequest.getParameter("webstart");
/*  61 */       if (??? != null)
/*     */       {
/*     */ 
/*  64 */         String str = paramHttpServletRequest.getScheme();
/*  65 */         str = str.trim();
/*     */         
/*     */ 
/*  68 */         if (((String)???).equals("installed"))
/*     */         {
/*     */ 
/*     */ 
/*  72 */           JnlpFileUpdater localJnlpFileUpdater = new JnlpFileUpdater(null, str);
/*     */           
/*  74 */           paramHttpServletResponse.sendRedirect("../conf/WebNMS.jnlp");
/*     */         }
/*  76 */         else if (((String)???).equals("notinstalled"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*  81 */           localJspWriter.write("\n<!---- to download page - start ---->\n<table border=0 width=100%><tr><td width=99%>\n<!-- text start -->\n<b> <font color=\"red\"> <i>Java Web Start is not installed in this machine. Please download Web Start. </i></font> </b>\n<!-- text End -->\n</td><td>\n<a href=\"http://java.sun.com/cgi-bin/javawebstart-platform.sh?\"><img src=\"../images/dl-javawebstart3.jpg\" border=0 hspace=20></a>\n</td></tr></table>\n\n<BR>\n<hr size=-1>\n<BR>\n\n<center>\n<img src=\"../images/jws-wordsonly.gif\">\n</center>\n<p align=justify>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Java(TM) Web Start, 'A new client application-deployment technology'  that gives you the power to launch full-featured applications with a single click from your Web browser. You can now download and launch applications, without going through complicated installation procedures. </P>\n\n<p align=justify>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Now 'Web NMS Application Client' is Web Start Enabled. You can launch it by just clicking on a Web page link. If the Application Client is not installed in your computer, Java Web Start automatically downloads all the necessary files. It then caches the files on your computer so that the client is always ready to be launched whenever you want -- either from an icon on your desktop or from the browser link. No matter how and when you launch, Web Start ensures that you always work in the latest version of the Application Client, available for access.  </P>\n\n<!---- to download page - end ---->\n\t\t\t");
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/*  94 */         localJspWriter.write("\n\t\t\tSorry, required parameters missing\n\t\t");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */       localJspWriter.write("\n</body>\n</html>\n\n");
/*     */ 
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 107 */       if ((localJspWriter != null) && (localJspWriter.getBufferSize() != 0))
/* 108 */         localJspWriter.clearBuffer();
/* 109 */       if (localPageContext != null) localPageContext.handlePageException(localThrowable);
/*     */     } finally {
/* 111 */       if (localJspFactory != null) localJspFactory.releasePageContext(localPageContext);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\jsp\WebStart.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */