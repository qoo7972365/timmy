/*    */ package sun.net.httpserver;
/*    */ 
/*    */ import java.util.LinkedList;
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
/*    */ class ContextList
/*    */ {
/*    */   static final int MAX_CONTEXTS = 50;
/* 36 */   LinkedList<HttpContextImpl> list = new LinkedList<>();
/*    */   
/*    */   public synchronized void add(HttpContextImpl paramHttpContextImpl) {
/* 39 */     assert paramHttpContextImpl.getPath() != null;
/* 40 */     this.list.add(paramHttpContextImpl);
/*    */   }
/*    */   
/*    */   public synchronized int size() {
/* 44 */     return this.list.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   synchronized HttpContextImpl findContext(String paramString1, String paramString2) {
/* 51 */     return findContext(paramString1, paramString2, false);
/*    */   }
/*    */   
/*    */   synchronized HttpContextImpl findContext(String paramString1, String paramString2, boolean paramBoolean) {
/* 55 */     paramString1 = paramString1.toLowerCase();
/* 56 */     String str = "";
/* 57 */     HttpContextImpl httpContextImpl = null;
/* 58 */     for (HttpContextImpl httpContextImpl1 : this.list) {
/* 59 */       if (!httpContextImpl1.getProtocol().equals(paramString1)) {
/*    */         continue;
/*    */       }
/* 62 */       String str1 = httpContextImpl1.getPath();
/* 63 */       if (paramBoolean && !str1.equals(paramString2))
/*    */         continue; 
/* 65 */       if (!paramBoolean && !paramString2.startsWith(str1)) {
/*    */         continue;
/*    */       }
/* 68 */       if (str1.length() > str.length()) {
/* 69 */         str = str1;
/* 70 */         httpContextImpl = httpContextImpl1;
/*    */       } 
/*    */     } 
/* 73 */     return httpContextImpl;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void remove(String paramString1, String paramString2) throws IllegalArgumentException {
/* 79 */     HttpContextImpl httpContextImpl = findContext(paramString1, paramString2, true);
/* 80 */     if (httpContextImpl == null) {
/* 81 */       throw new IllegalArgumentException("cannot remove element from list");
/*    */     }
/* 83 */     this.list.remove(httpContextImpl);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void remove(HttpContextImpl paramHttpContextImpl) throws IllegalArgumentException {
/* 89 */     for (HttpContextImpl httpContextImpl : this.list) {
/* 90 */       if (httpContextImpl.equals(paramHttpContextImpl)) {
/* 91 */         this.list.remove(httpContextImpl);
/*    */         return;
/*    */       } 
/*    */     } 
/* 95 */     throw new IllegalArgumentException("no such context in list");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/ContextList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */