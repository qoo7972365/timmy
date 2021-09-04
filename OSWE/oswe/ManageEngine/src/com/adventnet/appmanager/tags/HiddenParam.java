/*    */ package com.adventnet.appmanager.tags;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ public class HiddenParam extends TagSupport
/*    */ {
/* 11 */   private String name = null;
/*    */   
/*    */   public String getName() {
/* 14 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 18 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/*    */     try
/*    */     {
/* 26 */       JspWriter out = this.pageContext.getOut();
/* 27 */       String value = ((HttpServletRequest)this.pageContext.getRequest()).getParameter(this.name);
/* 28 */       if (value != null)
/*    */       {
/* 30 */         out.println("<input type=\"hidden\" name=\"" + this.name + "\" value=\"" + value + "\">");
/*    */       }
/*    */       
/*    */ 
/*    */     }
/*    */     catch (Exception ee)
/*    */     {
/*    */ 
/* 38 */       ee.printStackTrace();
/*    */     }
/* 40 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\HiddenParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */