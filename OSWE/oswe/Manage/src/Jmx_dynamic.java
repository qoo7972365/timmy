/*     */ import com.adventnet.agent.logging.Log;
/*     */ import com.adventnet.agent.logging.LogFactory;
/*     */ import com.adventnet.html.dynamic.DynamicError;
/*     */ import com.adventnet.html.dynamic.DynamicMain;
/*     */ import com.adventnet.html.dynamic.HtmlCreation;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.GenericServlet;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jmx_dynamic
/*     */   extends HttpServlet
/*     */ {
/*     */   private DynamicMain main;
/*     */   private Log log;
/*     */   
/*     */   public void init(ServletConfig paramServletConfig)
/*     */     throws ServletException
/*     */   {
/*  45 */     super.init(paramServletConfig);
/*     */     try
/*     */     {
/*  48 */       this.log = LogFactory.getInstance("HTML");
/*     */     }
/*     */     catch (InstantiationException localInstantiationException)
/*     */     {
/*  52 */       localInstantiationException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  66 */     paramHttpServletResponse.setContentType("text/html");
/*  67 */     String str1 = null;
/*  68 */     paramHttpServletRequest.getSession(true);
/*  69 */     this.main = new DynamicMain();
/*     */     
/*  71 */     HtmlCreation localHtmlCreation = new HtmlCreation();
/*     */     try
/*     */     {
/*  74 */       localHtmlCreation.setContextPath("../");
/*     */     } catch (NoSuchMethodError localNoSuchMethodError) {
/*  76 */       localHtmlCreation.setContextPath("");
/*     */     }
/*     */     
/*  79 */     this.main.registerHtmlInterface(localHtmlCreation);
/*  80 */     this.main.registerErrorInterface(new DynamicError());
/*     */     
/*  82 */     Hashtable localHashtable = new Hashtable();
/*     */     
/*  84 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/*     */     
/*  86 */     String str2 = localHttpSession.getId();
/*     */     
/*     */ 
/*  89 */     localHashtable.put("Session", localHttpSession);
/*  90 */     Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
/*     */     
/*  92 */     while (localEnumeration.hasMoreElements()) {
/*  93 */       localObject = localEnumeration.nextElement();
/*  94 */       String str3 = paramHttpServletRequest.getParameter(localObject.toString());
/*  95 */       this.log.trace("ParamName is " + localObject + "   " + "ParamValue is " + str3);
/*     */       
/*  97 */       if (str3.trim().length() != 0)
/*     */       {
/*  99 */         localHashtable.put(localObject, str3);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 104 */     Object localObject = paramHttpServletResponse.getWriter();
/*     */     
/*     */ 
/* 107 */     str1 = this.main.processGetRequest(localHashtable);
/*     */     
/*     */ 
/*     */ 
/* 111 */     ((PrintWriter)localObject).println(str1);
/* 112 */     ((PrintWriter)localObject).flush();
/* 113 */     ((PrintWriter)localObject).close();
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/* 119 */     paramHttpServletResponse.setContentType("text/html");
/* 120 */     String str1 = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */     this.main = new DynamicMain();
/*     */     
/* 128 */     HtmlCreation localHtmlCreation = new HtmlCreation();
/*     */     
/*     */     try
/*     */     {
/* 132 */       localHtmlCreation.setContextPath("../");
/*     */     }
/*     */     catch (NoSuchMethodError localNoSuchMethodError)
/*     */     {
/* 136 */       localHtmlCreation.setContextPath("");
/*     */     }
/*     */     
/* 139 */     this.main.registerHtmlInterface(localHtmlCreation);
/* 140 */     this.main.registerErrorInterface(new DynamicError());
/*     */     
/* 142 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/*     */     
/* 144 */     String str2 = paramHttpServletRequest.getParameter("attrbName");
/* 145 */     Hashtable localHashtable = new Hashtable();
/* 146 */     localHashtable.put("Session", localHttpSession);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */     Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
/*     */     Object localObject;
/* 158 */     while (localEnumeration.hasMoreElements()) {
/* 159 */       localObject = localEnumeration.nextElement();
/* 160 */       String str3 = paramHttpServletRequest.getParameter(localObject.toString());
/* 161 */       this.log.trace("ParamName is " + localObject + "   " + "ParamValue is " + str3);
/* 162 */       if (str3.trim().length() != 0)
/*     */       {
/* 164 */         localHashtable.put(localObject, str3);
/*     */       }
/*     */     }
/*     */     
/* 168 */     if (localHashtable != null)
/*     */     {
/* 170 */       localObject = paramHttpServletResponse.getWriter();
/*     */       
/* 172 */       str1 = this.main.processPostRequest(localHashtable);
/*     */       
/* 174 */       ((PrintWriter)localObject).println(str1);
/* 175 */       ((PrintWriter)localObject).flush();
/* 176 */       ((PrintWriter)localObject).close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\Jmx_dynamic.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */