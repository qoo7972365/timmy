/*    */ package com.adventnet.appmanager.tags;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadTime
/*    */   extends TagSupport
/*    */ {
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/*    */     try
/*    */     {
/* 22 */       JspWriter out = this.pageContext.getOut();
/* 23 */       Long starttime = (Long)((HttpServletRequest)this.pageContext.getRequest()).getAttribute("starttime");
/* 24 */       long wlendtime = System.currentTimeMillis();
/* 25 */       if (starttime != null)
/*    */       {
/* 27 */         long wlstartime = starttime.longValue();
/* 28 */         out.println(FormatUtil.formatNumber("" + (wlendtime - wlstartime)));
/*    */ 
/*    */       }
/*    */       else
/*    */       {
/* 33 */         out.println("0");
/*    */       }
/*    */       
/*    */     }
/*    */     catch (Exception ee)
/*    */     {
/* 39 */       ee.printStackTrace();
/*    */     }
/* 41 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\LoadTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */