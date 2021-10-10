/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.encoding.CorbaOutputObject;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import java.io.IOException;
/*     */ import org.omg.CORBA.portable.InputStream;
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
/*     */ public class CDROutputObject
/*     */   extends CorbaOutputObject
/*     */ {
/*     */   private Message header;
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private OMGSystemException omgWrapper;
/*     */   private CorbaConnection connection;
/*     */   
/*     */   private CDROutputObject(ORB paramORB, GIOPVersion paramGIOPVersion, Message paramMessage, BufferManagerWrite paramBufferManagerWrite, byte paramByte, CorbaMessageMediator paramCorbaMessageMediator) {
/*  79 */     super(paramORB, paramGIOPVersion, paramMessage.getEncodingVersion(), false, paramBufferManagerWrite, paramByte, (paramCorbaMessageMediator != null && paramCorbaMessageMediator
/*     */         
/*  81 */         .getConnection() != null) ? ((CorbaConnection)paramCorbaMessageMediator
/*  82 */         .getConnection())
/*  83 */         .shouldUseDirectByteBuffers() : false);
/*     */     
/*  85 */     this.header = paramMessage;
/*  86 */     this.orb = paramORB;
/*  87 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*  88 */     this.omgWrapper = OMGSystemException.get(paramORB, "rpc.encoding");
/*     */     
/*  90 */     getBufferManager().setOutputObject(this);
/*  91 */     this.corbaMessageMediator = paramCorbaMessageMediator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CDROutputObject(ORB paramORB, MessageMediator paramMessageMediator, Message paramMessage, byte paramByte) {
/*  99 */     this(paramORB, ((CorbaMessageMediator)paramMessageMediator)
/*     */         
/* 101 */         .getGIOPVersion(), paramMessage, 
/*     */         
/* 103 */         BufferManagerFactory.newBufferManagerWrite(((CorbaMessageMediator)paramMessageMediator)
/* 104 */           .getGIOPVersion(), paramMessage
/* 105 */           .getEncodingVersion(), paramORB), paramByte, (CorbaMessageMediator)paramMessageMediator);
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
/*     */   public CDROutputObject(ORB paramORB, MessageMediator paramMessageMediator, Message paramMessage, byte paramByte, int paramInt) {
/* 120 */     this(paramORB, ((CorbaMessageMediator)paramMessageMediator)
/*     */         
/* 122 */         .getGIOPVersion(), paramMessage, 
/*     */ 
/*     */         
/* 125 */         BufferManagerFactory.newBufferManagerWrite(paramInt, paramMessage
/* 126 */           .getEncodingVersion(), paramORB), paramByte, (CorbaMessageMediator)paramMessageMediator);
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
/*     */   public CDROutputObject(ORB paramORB, CorbaMessageMediator paramCorbaMessageMediator, GIOPVersion paramGIOPVersion, CorbaConnection paramCorbaConnection, Message paramMessage, byte paramByte) {
/* 140 */     this(paramORB, paramGIOPVersion, paramMessage, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         BufferManagerFactory.newBufferManagerWrite(paramGIOPVersion, paramMessage
/* 146 */           .getEncodingVersion(), paramORB), paramByte, paramCorbaMessageMediator);
/*     */ 
/*     */ 
/*     */     
/* 150 */     this.connection = paramCorbaConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message getMessageHeader() {
/* 158 */     return this.header;
/*     */   }
/*     */   
/*     */   public final void finishSendingMessage() {
/* 162 */     getBufferManager().sendMessage();
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
/*     */   
/*     */   public void writeTo(CorbaConnection paramCorbaConnection) throws IOException {
/* 179 */     ByteBufferWithInfo byteBufferWithInfo = getByteBufferWithInfo();
/*     */     
/* 181 */     getMessageHeader().setSize(byteBufferWithInfo.byteBuffer, byteBufferWithInfo.getSize());
/*     */     
/* 183 */     if (orb() != null) {
/* 184 */       if (((ORB)orb()).transportDebugFlag) {
/* 185 */         dprint(".writeTo: " + paramCorbaConnection);
/*     */       }
/* 187 */       if (((ORB)orb()).giopDebugFlag) {
/* 188 */         CDROutputStream_1_0.printBuffer(byteBufferWithInfo);
/*     */       }
/*     */     } 
/* 191 */     byteBufferWithInfo.byteBuffer.position(0).limit(byteBufferWithInfo.getSize());
/* 192 */     paramCorbaConnection.write(byteBufferWithInfo.byteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream create_input_stream() {
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorbaConnection getConnection() {
/* 207 */     if (this.connection != null) {
/* 208 */       return this.connection;
/*     */     }
/* 210 */     return (CorbaConnection)this.corbaMessageMediator.getConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBufferWithInfo getByteBufferWithInfo() {
/* 218 */     return super.getByteBufferWithInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setByteBufferWithInfo(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 223 */     super.setByteBufferWithInfo(paramByteBufferWithInfo);
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
/*     */   protected CodeSetConversion.CTBConverter createCharCTBConverter() {
/* 236 */     CodeSetComponentInfo.CodeSetContext codeSetContext = getCodeSets();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     if (codeSetContext == null) {
/* 242 */       return super.createCharCTBConverter();
/*     */     }
/*     */     
/* 245 */     OSFCodeSetRegistry.Entry entry = OSFCodeSetRegistry.lookupEntry(codeSetContext.getCharCodeSet());
/*     */     
/* 247 */     if (entry == null) {
/* 248 */       throw this.wrapper.unknownCodeset(entry);
/*     */     }
/* 250 */     return CodeSetConversion.impl().getCTBConverter(entry, 
/* 251 */         isLittleEndian(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected CodeSetConversion.CTBConverter createWCharCTBConverter() {
/* 257 */     CodeSetComponentInfo.CodeSetContext codeSetContext = getCodeSets();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (codeSetContext == null) {
/* 263 */       if (getConnection().isServer()) {
/* 264 */         throw this.omgWrapper.noClientWcharCodesetCtx();
/*     */       }
/* 266 */       throw this.omgWrapper.noServerWcharCodesetCmp();
/*     */     } 
/*     */ 
/*     */     
/* 270 */     OSFCodeSetRegistry.Entry entry = OSFCodeSetRegistry.lookupEntry(codeSetContext.getWCharCodeSet());
/*     */     
/* 272 */     if (entry == null) {
/* 273 */       throw this.wrapper.unknownCodeset(entry);
/*     */     }
/*     */     
/* 276 */     boolean bool = ((ORB)orb()).getORBData().useByteOrderMarkers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     if (entry == OSFCodeSetRegistry.UTF_16) {
/* 286 */       if (getGIOPVersion().equals(GIOPVersion.V1_2)) {
/* 287 */         return CodeSetConversion.impl().getCTBConverter(entry, false, bool);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (getGIOPVersion().equals(GIOPVersion.V1_1)) {
/* 293 */         return CodeSetConversion.impl().getCTBConverter(entry, 
/* 294 */             isLittleEndian(), false);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 300 */     return CodeSetConversion.impl().getCTBConverter(entry, 
/* 301 */         isLittleEndian(), bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CodeSetComponentInfo.CodeSetContext getCodeSets() {
/* 311 */     if (getConnection() == null) {
/* 312 */       return CodeSetComponentInfo.LOCAL_CODE_SETS;
/*     */     }
/* 314 */     return getConnection().getCodeSetContext();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 319 */     ORBUtility.dprint("CDROutputObject", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDROutputObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */