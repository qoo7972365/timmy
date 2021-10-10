/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*     */ import com.sun.corba.se.impl.encoding.CDROutputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*     */ import java.io.IOException;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*     */ 
/*     */ public final class RequestMessage_1_2
/*     */   extends Message_1_2
/*     */   implements RequestMessage
/*     */ {
/*  53 */   private ORB orb = null;
/*  54 */   private ORBUtilSystemException wrapper = null;
/*  55 */   private byte response_flags = 0;
/*  56 */   private byte[] reserved = null;
/*  57 */   private TargetAddress target = null;
/*  58 */   private String operation = null;
/*  59 */   private ServiceContexts service_contexts = null;
/*  60 */   private ObjectKey objectKey = null;
/*     */ 
/*     */ 
/*     */   
/*     */   RequestMessage_1_2(ORB paramORB) {
/*  65 */     this.orb = paramORB;
/*  66 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RequestMessage_1_2(ORB paramORB, int paramInt, byte paramByte, byte[] paramArrayOfbyte, TargetAddress paramTargetAddress, String paramString, ServiceContexts paramServiceContexts) {
/*  73 */     super(1195986768, GIOPVersion.V1_2, (byte)0, (byte)0, 0);
/*     */     
/*  75 */     this.orb = paramORB;
/*  76 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/*  78 */     this.request_id = paramInt;
/*  79 */     this.response_flags = paramByte;
/*  80 */     this.reserved = paramArrayOfbyte;
/*  81 */     this.target = paramTargetAddress;
/*  82 */     this.operation = paramString;
/*  83 */     this.service_contexts = paramServiceContexts;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestId() {
/*  89 */     return this.request_id;
/*     */   }
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
/*     */   public boolean isResponseExpected() {
/* 105 */     if ((this.response_flags & 0x1) == 1) {
/* 106 */       return true;
/*     */     }
/*     */     
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] getReserved() {
/* 113 */     return this.reserved;
/*     */   }
/*     */   
/*     */   public ObjectKey getObjectKey() {
/* 117 */     if (this.objectKey == null)
/*     */     {
/* 119 */       this.objectKey = MessageBase.extractObjectKey(this.target, this.orb);
/*     */     }
/*     */     
/* 122 */     return this.objectKey;
/*     */   }
/*     */   
/*     */   public String getOperation() {
/* 126 */     return this.operation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getPrincipal() {
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   public ServiceContexts getServiceContexts() {
/* 135 */     return this.service_contexts;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(InputStream paramInputStream) {
/* 141 */     super.read(paramInputStream);
/* 142 */     this.request_id = paramInputStream.read_ulong();
/* 143 */     this.response_flags = paramInputStream.read_octet();
/* 144 */     this.reserved = new byte[3];
/* 145 */     for (byte b = 0; b < 3; b++) {
/* 146 */       this.reserved[b] = paramInputStream.read_octet();
/*     */     }
/* 148 */     this.target = TargetAddressHelper.read(paramInputStream);
/* 149 */     getObjectKey();
/* 150 */     this.operation = paramInputStream.read_string();
/* 151 */     this.service_contexts = new ServiceContexts((InputStream)paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     ((CDRInputStream)paramInputStream).setHeaderPadding(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 164 */     super.write(paramOutputStream);
/* 165 */     paramOutputStream.write_ulong(this.request_id);
/* 166 */     paramOutputStream.write_octet(this.response_flags);
/* 167 */     nullCheck(this.reserved);
/* 168 */     if (this.reserved.length != 3) {
/* 169 */       throw this.wrapper.badReservedLength(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */     
/* 172 */     for (byte b = 0; b < 3; b++) {
/* 173 */       paramOutputStream.write_octet(this.reserved[b]);
/*     */     }
/* 175 */     nullCheck(this.target);
/* 176 */     TargetAddressHelper.write(paramOutputStream, this.target);
/* 177 */     paramOutputStream.write_string(this.operation);
/* 178 */     if (this.service_contexts != null) {
/* 179 */       this.service_contexts.write((OutputStream)paramOutputStream, GIOPVersion.V1_2);
/*     */     }
/*     */     else {
/*     */       
/* 183 */       ServiceContexts.writeNullServiceContext((OutputStream)paramOutputStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     ((CDROutputStream)paramOutputStream).setHeaderPadding(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void callback(MessageHandler paramMessageHandler) throws IOException {
/* 198 */     paramMessageHandler.handleInput(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/RequestMessage_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */