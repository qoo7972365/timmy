/*    */ package com.adventnet.awolf.tags;
/*    */ 
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.BodyContent;
/*    */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoomImage
/*    */   extends BodyTagSupport
/*    */ {
/* 33 */   private String name = null;
/*    */   
/*    */   public static void main(String[] args) {}
/*    */   
/*    */   public String getName()
/*    */   {
/* 39 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 45 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int doEndTag() throws JspException
/*    */   {
/*    */     try {
/* 51 */       String str = this.bodyContent.getString();
/* 52 */       JspWriter out = this.pageContext.getOut();
/* 53 */       out.println("<a href=\"javascript:void(0)\" onClick=\"window.open('/webclient/temp/" + this.pageContext.getAttribute("zoomedimage") + "','','scrollbars=yes')\"  class=\"staticlinks\">" + str + "</a>");
/*    */     }
/*    */     catch (Exception e) {}
/*    */     
/*    */ 
/*    */ 
/* 59 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\ZoomImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */