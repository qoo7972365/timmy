/*    */ package com.adventnet.appmanager.filter;
/*    */ 
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class LoadTimeCalculator implements javax.servlet.Filter
/*    */ {
/* 11 */   private FilterConfig config = null;
/* 12 */   private javax.servlet.ServletContext moServletContext = null;
/*    */   
/*    */   public void init(FilterConfig config)
/*    */     throws ServletException
/*    */   {
/* 17 */     this.config = config;
/* 18 */     this.moServletContext = config.getServletContext();
/*    */   }
/*    */   
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 24 */     this.config = null;
/*    */   }
/*    */   
/*    */   public void doFilter(ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
/*    */     throws java.io.IOException, ServletException
/*    */   {
/* 30 */     if ((request instanceof HttpServletRequest))
/*    */     {
/* 32 */       long starttime = System.currentTimeMillis();
/* 33 */       HttpServletRequest httprequest = (HttpServletRequest)request;
/* 34 */       Long wlStarttime = (Long)httprequest.getAttribute("starttime");
/* 35 */       if (wlStarttime == null)
/*    */       {
/* 37 */         request.setAttribute("starttime", new Long(starttime));
/*    */       }
/*    */     }
/*    */     
/* 41 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public FilterConfig getFilterConfig()
/*    */   {
/* 49 */     return this.config;
/*    */   }
/*    */   
/*    */ 
/*    */   public void setFilterConfig(FilterConfig cfg)
/*    */   {
/*    */     try
/*    */     {
/* 57 */       init(cfg);
/*    */     }
/*    */     catch (ServletException ee) {}
/*    */   }
/*    */   
/*    */   private void log(Object o) {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\LoadTimeCalculator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */