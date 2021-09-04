/*    */ package com.adventnet.appmanager.tags;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.BodyContent;
/*    */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormatTag
/*    */   extends BodyTagSupport
/*    */ {
/* 15 */   private String type = "Date";
/*    */   
/*    */   public String getType() {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 22 */     this.type = type;
/*    */   }
/*    */   
/*    */ 
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 29 */     String str = this.bodyContent.getString();
/*    */     
/*    */     try
/*    */     {
/* 33 */       JspWriter out = this.pageContext.getOut();
/* 34 */       if ("Date".equalsIgnoreCase(this.type)) {
/* 35 */         out.println(FormatUtil.formatDT(str));
/* 36 */       } else if ("Number".equalsIgnoreCase(this.type)) {
/* 37 */         out.println(FormatUtil.formatNumber(str));
/*    */       } else {
/* 39 */         out.println(str);
/*    */       }
/*    */     }
/*    */     catch (Exception ee) {}
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 49 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\FormatTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */