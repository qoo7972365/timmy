/*     */ package com.adventnet.appmanager.tags;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ 
/*     */ public class AdminLink
/*     */   extends BodyTagSupport
/*     */ {
/*  13 */   private String href = null;
/*  14 */   private String enableclass = "links";
/*  15 */   private String disableclass = "disabledlink";
/*     */   
/*     */   public String getHref() {
/*  18 */     return this.href;
/*     */   }
/*     */   
/*     */   public void setHref(String href) {
/*  22 */     this.href = href;
/*     */   }
/*     */   
/*     */   public String getEnableClass()
/*     */   {
/*  27 */     return this.enableclass;
/*     */   }
/*     */   
/*     */   public void setEnableClass(String enableclass) {
/*  31 */     this.enableclass = enableclass;
/*     */   }
/*     */   
/*     */   public String getDisableClass()
/*     */   {
/*  36 */     return this.disableclass;
/*     */   }
/*     */   
/*     */ 
/*  40 */   public void setDisableClass(String disableclass) { this.disableclass = disableclass; }
/*     */   
/*  42 */   private int access = 100;
/*     */   
/*     */   public int getAccess() {
/*  45 */     return this.access;
/*     */   }
/*     */   
/*     */   public void setAccess(int access) {
/*  49 */     this.access = access;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  59 */     String str = this.bodyContent.getString();
/*     */     try
/*     */     {
/*  62 */       JspWriter out = this.pageContext.getOut();
/*  63 */       boolean isAdmin = ((HttpServletRequest)this.pageContext.getRequest()).isUserInRole("ADMIN");
/*  64 */       boolean isDemo = ((HttpServletRequest)this.pageContext.getRequest()).isUserInRole("DEMO");
/*  65 */       if (this.href == null)
/*     */       {
/*  67 */         this.href = ((String)this.pageContext.findAttribute("href"));
/*     */       }
/*  69 */       if (isAdmin)
/*     */       {
/*  71 */         out.print("<a href=\"" + this.href + "\" class=\"" + this.enableclass + "\">");
/*  72 */         out.print(str);
/*  73 */         out.print("</a>");
/*     */       }
/*  75 */       else if (isDemo)
/*     */       {
/*  77 */         int isDemoallowed = 0;
/*  78 */         isDemoallowed = this.access % 100 - this.access % 100 % 10 / 10;
/*  79 */         if (isDemoallowed == 0)
/*     */         {
/*  81 */           out.print("<a href=\"javascript:alertUser()\" class=\"" + this.enableclass + "\">");
/*  82 */           out.print(str);
/*  83 */           out.print("</a>");
/*     */         }
/*     */         else
/*     */         {
/*  87 */           out.print("<a href=\"" + this.href + "\" class=\"" + this.enableclass + "\">");
/*  88 */           out.print(str);
/*  89 */           out.print("</a>");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  94 */         out.print("<a href=\"javascript:void(0)\" class=\"" + this.disableclass + "\">");
/*  95 */         out.print(str);
/*  96 */         out.print("</a>");
/*     */       }
/*     */     }
/*     */     catch (Exception ee) {}
/*     */     
/*     */ 
/*     */ 
/* 103 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 110 */     String str = this.bodyContent.getString();
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
/* 121 */     return 6;
/*     */   }
/*     */   
/*     */   public void doInitBody()
/*     */     throws JspException
/*     */   {
/* 127 */     String str = this.bodyContent.getString();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\AdminLink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */