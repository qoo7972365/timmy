/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import java.io.IOException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FragmentMessage_1_2
/*     */   extends Message_1_2
/*     */   implements FragmentMessage
/*     */ {
/*     */   FragmentMessage_1_2() {}
/*     */   
/*     */   FragmentMessage_1_2(int paramInt) {
/*  45 */     super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)7, 0);
/*     */     
/*  47 */     this.message_type = 7;
/*  48 */     this.request_id = paramInt;
/*     */   }
/*     */   
/*     */   FragmentMessage_1_2(Message_1_1 paramMessage_1_1) {
/*  52 */     this.magic = paramMessage_1_1.magic;
/*  53 */     this.GIOP_version = paramMessage_1_1.GIOP_version;
/*  54 */     this.flags = paramMessage_1_1.flags;
/*  55 */     this.message_type = 7;
/*  56 */     this.message_size = 0;
/*     */     
/*  58 */     switch (paramMessage_1_1.message_type) {
/*     */       case 0:
/*  60 */         this.request_id = ((RequestMessage)paramMessage_1_1).getRequestId();
/*     */         break;
/*     */       case 1:
/*  63 */         this.request_id = ((ReplyMessage)paramMessage_1_1).getRequestId();
/*     */         break;
/*     */       case 3:
/*  66 */         this.request_id = ((LocateRequestMessage)paramMessage_1_1).getRequestId();
/*     */         break;
/*     */       case 4:
/*  69 */         this.request_id = ((LocateReplyMessage)paramMessage_1_1).getRequestId();
/*     */         break;
/*     */       case 7:
/*  72 */         this.request_id = ((FragmentMessage)paramMessage_1_1).getRequestId();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestId() {
/*  80 */     return this.request_id;
/*     */   }
/*     */   
/*     */   public int getHeaderLength() {
/*  84 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(InputStream paramInputStream) {
/*  94 */     super.read(paramInputStream);
/*  95 */     this.request_id = paramInputStream.read_ulong();
/*     */   }
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/*  99 */     super.write(paramOutputStream);
/* 100 */     paramOutputStream.write_ulong(this.request_id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void callback(MessageHandler paramMessageHandler) throws IOException {
/* 106 */     paramMessageHandler.handleInput(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/FragmentMessage_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */