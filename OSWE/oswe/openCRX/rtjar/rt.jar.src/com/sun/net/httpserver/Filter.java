/*    */ package com.sun.net.httpserver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ import jdk.Exported;
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
/*    */ @Exported
/*    */ public abstract class Filter
/*    */ {
/*    */   public abstract void doFilter(HttpExchange paramHttpExchange, Chain paramChain) throws IOException;
/*    */   
/*    */   public abstract String description();
/*    */   
/*    */   @Exported
/*    */   public static class Chain
/*    */   {
/*    */     private ListIterator<Filter> iter;
/*    */     private HttpHandler handler;
/*    */     
/*    */     public Chain(List<Filter> param1List, HttpHandler param1HttpHandler) {
/* 61 */       this.iter = param1List.listIterator();
/* 62 */       this.handler = param1HttpHandler;
/*    */     }
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
/*    */     public void doFilter(HttpExchange param1HttpExchange) throws IOException {
/* 78 */       if (!this.iter.hasNext()) {
/* 79 */         this.handler.handle(param1HttpExchange);
/*    */       } else {
/* 81 */         Filter filter = this.iter.next();
/* 82 */         filter.doFilter(param1HttpExchange, this);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */