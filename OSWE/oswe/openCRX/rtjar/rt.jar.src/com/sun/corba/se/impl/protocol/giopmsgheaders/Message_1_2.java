/*    */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.omg.CORBA.portable.OutputStream;
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
/*    */ public class Message_1_2
/*    */   extends Message_1_1
/*    */ {
/* 33 */   protected int request_id = 0;
/*    */ 
/*    */   
/*    */   Message_1_2() {}
/*    */ 
/*    */   
/*    */   Message_1_2(int paramInt1, GIOPVersion paramGIOPVersion, byte paramByte1, byte paramByte2, int paramInt2) {
/* 40 */     super(paramInt1, paramGIOPVersion, paramByte1, paramByte2, paramInt2);
/*    */   }
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
/*    */   public void unmarshalRequestID(ByteBuffer paramByteBuffer) {
/*    */     int i, j, k, m;
/* 55 */     if (!isLittleEndian()) {
/* 56 */       i = paramByteBuffer.get(12) << 24 & 0xFF000000;
/* 57 */       j = paramByteBuffer.get(13) << 16 & 0xFF0000;
/* 58 */       k = paramByteBuffer.get(14) << 8 & 0xFF00;
/* 59 */       m = paramByteBuffer.get(15) << 0 & 0xFF;
/*    */     } else {
/* 61 */       i = paramByteBuffer.get(15) << 24 & 0xFF000000;
/* 62 */       j = paramByteBuffer.get(14) << 16 & 0xFF0000;
/* 63 */       k = paramByteBuffer.get(13) << 8 & 0xFF00;
/* 64 */       m = paramByteBuffer.get(12) << 0 & 0xFF;
/*    */     } 
/*    */     
/* 67 */     this.request_id = i | j | k | m;
/*    */   }
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 71 */     if (this.encodingVersion == 0) {
/* 72 */       super.write(paramOutputStream);
/*    */       return;
/*    */     } 
/* 75 */     GIOPVersion gIOPVersion = this.GIOP_version;
/* 76 */     this.GIOP_version = GIOPVersion.getInstance((byte)13, this.encodingVersion);
/*    */     
/* 78 */     super.write(paramOutputStream);
/* 79 */     this.GIOP_version = gIOPVersion;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/Message_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */