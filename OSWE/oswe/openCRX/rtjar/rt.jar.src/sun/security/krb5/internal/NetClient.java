/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public abstract class NetClient
/*    */   implements AutoCloseable
/*    */ {
/*    */   public static NetClient getInstance(String paramString1, String paramString2, int paramInt1, int paramInt2) throws IOException {
/* 42 */     if (paramString1.equals("TCP")) {
/* 43 */       return new TCPClient(paramString2, paramInt1, paramInt2);
/*    */     }
/* 45 */     return new UDPClient(paramString2, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public abstract void send(byte[] paramArrayOfbyte) throws IOException;
/*    */   
/*    */   public abstract byte[] receive() throws IOException;
/*    */   
/*    */   public abstract void close() throws IOException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/NetClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */