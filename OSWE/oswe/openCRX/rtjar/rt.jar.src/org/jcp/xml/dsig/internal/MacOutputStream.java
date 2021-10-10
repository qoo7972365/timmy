/*    */ package org.jcp.xml.dsig.internal;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import javax.crypto.Mac;
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
/*    */ public class MacOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   private final Mac mac;
/*    */   
/*    */   public MacOutputStream(Mac paramMac) {
/* 40 */     this.mac = paramMac;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int paramInt) {
/* 45 */     super.write(paramInt);
/* 46 */     this.mac.update((byte)paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 51 */     super.write(paramArrayOfbyte, paramInt1, paramInt2);
/* 52 */     this.mac.update(paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/MacOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */