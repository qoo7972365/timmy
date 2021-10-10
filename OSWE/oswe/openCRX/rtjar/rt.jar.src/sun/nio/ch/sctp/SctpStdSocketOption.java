/*    */ package sun.nio.ch.sctp;
/*    */ 
/*    */ import com.sun.nio.sctp.SctpSocketOption;
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
/*    */ public class SctpStdSocketOption<T>
/*    */   implements SctpSocketOption<T>
/*    */ {
/*    */   public static final int SCTP_DISABLE_FRAGMENTS = 1;
/*    */   public static final int SCTP_EXPLICIT_COMPLETE = 2;
/*    */   public static final int SCTP_FRAGMENT_INTERLEAVE = 3;
/*    */   public static final int SCTP_NODELAY = 4;
/*    */   public static final int SO_SNDBUF = 5;
/*    */   public static final int SO_RCVBUF = 6;
/*    */   public static final int SO_LINGER = 7;
/*    */   private final String name;
/*    */   private final Class<T> type;
/*    */   private int constValue;
/*    */   
/*    */   public SctpStdSocketOption(String paramString, Class<T> paramClass) {
/* 49 */     this.name = paramString;
/* 50 */     this.type = paramClass;
/*    */   }
/*    */   
/*    */   public SctpStdSocketOption(String paramString, Class<T> paramClass, int paramInt) {
/* 54 */     this.name = paramString;
/* 55 */     this.type = paramClass;
/* 56 */     this.constValue = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 61 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<T> type() {
/* 66 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return this.name;
/*    */   }
/*    */   
/*    */   int constValue() {
/* 75 */     return this.constValue;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpStdSocketOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */