/*     */ package com.adventnet.appmanager.tags;
/*     */ 
/*     */ import com.adventnet.appmanager.util.AppManagerUtil;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ 
/*     */ public class Truncate
/*     */   extends BodyTagSupport
/*     */ {
/*  13 */   private String tooltip = "false";
/*  14 */   private int length = 30;
/*     */   
/*     */   public String getTooltip() {
/*  17 */     return this.tooltip;
/*     */   }
/*     */   
/*     */   public void setTooltip(String tooltip) {
/*  21 */     this.tooltip = tooltip;
/*     */   }
/*     */   
/*     */   public int getLength()
/*     */   {
/*  26 */     return this.length;
/*     */   }
/*     */   
/*     */   public void setLength(int length) {
/*  30 */     this.length = length;
/*     */   }
/*     */   
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  36 */     String str = this.bodyContent.getString();
/*     */     try
/*     */     {
/*  39 */       JspWriter out = this.pageContext.getOut();
/*  40 */       if (this.tooltip.equals("true"))
/*     */       {
/*  42 */         out.println("<a class=\"tooltip\" title=\"" + str + "\">" + getTrimmedText(str, this.length) + "</a>");
/*     */       }
/*     */       else
/*     */       {
/*  46 */         out.println(getTrimmedText(str, this.length));
/*     */       }
/*     */     }
/*     */     catch (Exception ee) {}
/*     */     
/*     */ 
/*     */ 
/*  53 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/*  60 */     String str = this.bodyContent.getString();
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
/*  71 */     return 6;
/*     */   }
/*     */   
/*     */   public void doInitBody()
/*     */     throws JspException
/*     */   {
/*  77 */     String str = this.bodyContent.getString();
/*     */   }
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
/*     */   private String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*     */   {
/*  94 */     lengthOfTrimmedString = AppManagerUtil.getWebclientDisplayLength(lengthOfTrimmedString);
/*  95 */     if ((stringToTrim != null) && (lengthOfTrimmedString > 0))
/*     */     {
/*  97 */       if (stringToTrim.length() > lengthOfTrimmedString)
/*     */       {
/*  99 */         return stringToTrim.substring(0, lengthOfTrimmedString - 3) + "...";
/*     */       }
/*     */     }
/* 102 */     return stringToTrim;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\Truncate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */