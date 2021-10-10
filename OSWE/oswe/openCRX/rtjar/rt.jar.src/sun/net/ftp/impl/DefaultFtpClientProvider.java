/*    */ package sun.net.ftp.impl;
/*    */ 
/*    */ import sun.net.ftp.FtpClient;
/*    */ import sun.net.ftp.FtpClientProvider;
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
/*    */ public class DefaultFtpClientProvider
/*    */   extends FtpClientProvider
/*    */ {
/*    */   public FtpClient createFtpClient() {
/* 35 */     return FtpClient.create();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ftp/impl/DefaultFtpClientProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */