/*    */ package com.adventnet.awolf.tags;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ public class AMParam
/*    */   extends TagSupport
/*    */ {
/* 11 */   private String name = null;
/* 12 */   private String value = null;
/*    */   
/*    */   public String getName()
/*    */   {
/* 16 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 20 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getValue()
/*    */   {
/* 25 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 29 */     this.value = value;
/*    */   }
/*    */   
/*    */ 
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/*    */     try
/*    */     {
/* 38 */       TagSupport tag = (TagSupport)getParent();
/* 39 */       String id = tag.getId();
/*    */       
/* 41 */       Hashtable table = (Hashtable)this.pageContext.findAttribute(id);
/* 42 */       if (table == null)
/*    */       {
/* 44 */         table = new Hashtable();
/* 45 */         this.pageContext.setAttribute(id, table);
/*    */       }
/*    */       
/* 48 */       table.put(this.name, this.value);
/*    */     }
/*    */     catch (Exception ee) {}
/*    */     
/*    */ 
/* 53 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\AMParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */