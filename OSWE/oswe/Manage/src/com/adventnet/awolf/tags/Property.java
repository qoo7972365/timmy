/*    */ package com.adventnet.awolf.tags;
/*    */ 
/*    */ import javax.servlet.jsp.JspException;
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
/*    */ public class Property
/*    */   extends BodyTagSupport
/*    */ {
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 24 */     return 6;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int doAfterBody()
/*    */     throws JspException
/*    */   {
/* 33 */     return 6;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void doInitBody()
/*    */     throws JspException
/*    */   {}
/*    */   
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 46 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */