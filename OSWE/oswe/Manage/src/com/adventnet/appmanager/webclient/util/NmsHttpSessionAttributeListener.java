/*    */ package com.adventnet.appmanager.webclient.util;
/*    */ 
/*    */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*    */ import com.adventnet.management.log.LogUser;
/*    */ import com.adventnet.nms.util.NmsLogMgr;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionAttributeListener;
/*    */ import javax.servlet.http.HttpSessionBindingEvent;
/*    */ 
/*    */ 
/*    */ public class NmsHttpSessionAttributeListener
/*    */   implements HttpSessionAttributeListener
/*    */ {
/*    */   public void attributeAdded(HttpSessionBindingEvent event)
/*    */   {
/* 16 */     log(event, "added");
/*    */   }
/*    */   
/*    */   public void attributeRemoved(HttpSessionBindingEvent event)
/*    */   {
/* 21 */     log(event, "removed");
/*    */   }
/*    */   
/*    */   public void attributeReplaced(HttpSessionBindingEvent event)
/*    */   {
/* 26 */     log(event, "replaced");
/*    */   }
/*    */   
/* 29 */   public static int noofusers = 0;
/*    */   
/*    */   public static int getNoOfUsers()
/*    */   {
/* 33 */     return noofusers;
/*    */   }
/*    */   
/*    */   private void log(HttpSessionBindingEvent event, String operation) {
/* 37 */     int level = NmsLogMgr.MISCUSER.getLevel();
/*    */     
/*    */ 
/* 40 */     String name = event.getName();
/* 41 */     Object value = event.getValue();
/* 42 */     if (name.equals("userName"))
/*    */     {
/* 44 */       HttpSession sess = event.getSession();
/* 45 */       if (operation.equals("added"))
/*    */       {
/* 47 */         noofusers += 1;
/*    */       }
/* 49 */       else if (operation.equals("removed"))
/*    */       {
/* 51 */         if (sess != null)
/*    */         {
/* 53 */           long last = sess.getLastAccessedTime();
/* 54 */           if (last >= AMServerFramework.serverStartupTime)
/*    */           {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 60 */             noofusers -= 1;
/*    */           }
/*    */           
/*    */         }
/*    */         
/*    */       }
/*    */       else {
/* 67 */         noofusers += 1;
/*    */       }
/*    */     }
/*    */     
/* 71 */     StringBuffer sb = new StringBuffer();
/* 72 */     sb.append("Attribute ");
/* 73 */     sb.append(operation);
/* 74 */     sb.append(" in HttpSession. Attribute name is ");
/* 75 */     sb.append(name);
/* 76 */     sb.append(", Attribute value is ");
/* 77 */     sb.append(value);
/* 78 */     NmsLogMgr.MISCUSER.log(sb.toString(), 4);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\webclient\util\NmsHttpSessionAttributeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */