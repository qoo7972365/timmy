/*    */ package java.nio.file;
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
/*    */ 
/*    */ public final class StandardWatchEventKinds
/*    */ {
/* 47 */   public static final WatchEvent.Kind<Object> OVERFLOW = new StdWatchEventKind("OVERFLOW", Object.class);
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
/* 58 */   public static final WatchEvent.Kind<Path> ENTRY_CREATE = new StdWatchEventKind<>("ENTRY_CREATE", Path.class);
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
/* 69 */   public static final WatchEvent.Kind<Path> ENTRY_DELETE = new StdWatchEventKind<>("ENTRY_DELETE", Path.class);
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
/* 80 */   public static final WatchEvent.Kind<Path> ENTRY_MODIFY = new StdWatchEventKind<>("ENTRY_MODIFY", Path.class);
/*    */   
/*    */   private static class StdWatchEventKind<T> implements WatchEvent.Kind<T> {
/*    */     private final String name;
/*    */     private final Class<T> type;
/*    */     
/*    */     StdWatchEventKind(String param1String, Class<T> param1Class) {
/* 87 */       this.name = param1String;
/* 88 */       this.type = param1Class;
/*    */     }
/* 90 */     public String name() { return this.name; }
/* 91 */     public Class<T> type() { return this.type; } public String toString() {
/* 92 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/StandardWatchEventKinds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */