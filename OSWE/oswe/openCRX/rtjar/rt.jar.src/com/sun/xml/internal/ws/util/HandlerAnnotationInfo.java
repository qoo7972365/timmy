/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.xml.ws.handler.Handler;
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
/*    */ public class HandlerAnnotationInfo
/*    */ {
/*    */   private List<Handler> handlers;
/*    */   private Set<String> roles;
/*    */   
/*    */   public List<Handler> getHandlers() {
/* 54 */     return this.handlers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHandlers(List<Handler> handlers) {
/* 63 */     this.handlers = handlers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getRoles() {
/* 72 */     return this.roles;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRoles(Set<String> roles) {
/* 81 */     this.roles = roles;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/HandlerAnnotationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */