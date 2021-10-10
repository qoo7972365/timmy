/*    */ package jdk.net;
/*    */ 
/*    */ import java.net.SocketOption;
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
/*    */ @Exported
/*    */ public final class ExtendedSocketOptions
/*    */ {
/*    */   private static class ExtSocketOption<T>
/*    */     implements SocketOption<T>
/*    */   {
/*    */     private final String name;
/*    */     private final Class<T> type;
/*    */     
/*    */     ExtSocketOption(String param1String, Class<T> param1Class) {
/* 42 */       this.name = param1String;
/* 43 */       this.type = param1Class;
/*    */     }
/* 45 */     public String name() { return this.name; }
/* 46 */     public Class<T> type() { return this.type; } public String toString() {
/* 47 */       return this.name;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public static final SocketOption<SocketFlow> SO_FLOW_SLA = new ExtSocketOption<>("SO_FLOW_SLA", SocketFlow.class);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/net/ExtendedSocketOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */