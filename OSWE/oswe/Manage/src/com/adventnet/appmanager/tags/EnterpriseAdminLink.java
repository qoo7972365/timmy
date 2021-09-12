/*     */ package com.adventnet.appmanager.tags;
/*     */ 
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnterpriseAdminLink
/*     */   extends BodyTagSupport
/*     */ {
/*  16 */   private String href = null;
/*  17 */   private String enableclass = "links";
/*  18 */   private String disableclass = "disabledlink";
/*     */   
/*     */   public String getHref() {
/*  21 */     return this.href;
/*     */   }
/*     */   
/*     */   public void setHref(String href) {
/*  25 */     this.href = href;
/*     */   }
/*     */   
/*     */   public String getEnableClass()
/*     */   {
/*  30 */     return this.enableclass;
/*     */   }
/*     */   
/*     */   public void setEnableClass(String enableclass) {
/*  34 */     this.enableclass = enableclass;
/*     */   }
/*     */   
/*     */   public String getDisableClass()
/*     */   {
/*  39 */     return this.disableclass;
/*     */   }
/*     */   
/*     */ 
/*  43 */   public void setDisableClass(String disableclass) { this.disableclass = disableclass; }
/*     */   
/*  45 */   private int access = 100;
/*     */   
/*     */   public int getAccess() {
/*  48 */     return this.access;
/*     */   }
/*     */   
/*     */   public void setAccess(int access) {
/*  52 */     this.access = access;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  62 */     String str = this.bodyContent.getString();
/*     */     try
/*     */     {
/*  65 */       JspWriter out = this.pageContext.getOut();
/*  66 */       boolean isEnterpriseAdmin = ((HttpServletRequest)this.pageContext.getRequest()).isUserInRole("ENTERPRISEADMIN");
/*  67 */       boolean isDemoEnterprise = false;
/*  68 */       if (((HttpServletRequest)this.pageContext.getRequest()).getRemoteUser().equals("systemadmin_enterprise"))
/*     */       {
/*  70 */         isDemoEnterprise = true;
/*     */       }
/*     */       else
/*     */       {
/*  74 */         isDemoEnterprise = false;
/*     */       }
/*  76 */       if (this.href == null)
/*     */       {
/*  78 */         this.href = ((String)this.pageContext.findAttribute("href"));
/*     */       }
/*  80 */       if ((isEnterpriseAdmin) && (!isDemoEnterprise))
/*     */       {
/*  82 */         out.print("<a href=\"" + this.href + "\" class=\"" + this.enableclass + "\">");
/*  83 */         out.print(str);
/*  84 */         out.print("</a>");
/*     */       }
/*  86 */       else if ((isEnterpriseAdmin) && (isDemoEnterprise))
/*     */       {
/*  88 */         out.print("<a href=\"javascript:alertUser()\" class=\"" + this.enableclass + "\">");
/*  89 */         out.print(str);
/*  90 */         out.print("</a>");
/*     */       }
/*     */       else
/*     */       {
/*  94 */         out.print("<a href=\"" + this.href + "\" class=\"" + this.enableclass + "\">");
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


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\EnterpriseAdminLink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */