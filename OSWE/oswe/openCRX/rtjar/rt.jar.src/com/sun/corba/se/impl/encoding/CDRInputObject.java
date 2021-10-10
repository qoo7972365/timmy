/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.org.omg.SendingContext.CodeBase;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.omg.CORBA.ORB;
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
/*     */ public class CDRInputObject
/*     */   extends CDRInputStream
/*     */   implements InputObject
/*     */ {
/*     */   private CorbaConnection corbaConnection;
/*     */   private Message header;
/*     */   private boolean unmarshaledHeader;
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private OMGSystemException omgWrapper;
/*     */   
/*     */   public CDRInputObject(ORB paramORB, CorbaConnection paramCorbaConnection, ByteBuffer paramByteBuffer, Message paramMessage) {
/*  74 */     super((ORB)paramORB, paramByteBuffer, paramMessage.getSize(), paramMessage.isLittleEndian(), paramMessage
/*  75 */         .getGIOPVersion(), paramMessage.getEncodingVersion(), 
/*  76 */         BufferManagerFactory.newBufferManagerRead(paramMessage
/*  77 */           .getGIOPVersion(), paramMessage
/*  78 */           .getEncodingVersion(), paramORB));
/*     */ 
/*     */     
/*  81 */     this.corbaConnection = paramCorbaConnection;
/*  82 */     this.orb = paramORB;
/*  83 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */     
/*  85 */     this.omgWrapper = OMGSystemException.get(paramORB, "rpc.encoding");
/*     */ 
/*     */     
/*  88 */     if (paramORB.transportDebugFlag) {
/*  89 */       dprint(".CDRInputObject constructor:");
/*     */     }
/*     */     
/*  92 */     getBufferManager().init(paramMessage);
/*     */     
/*  94 */     this.header = paramMessage;
/*     */     
/*  96 */     this.unmarshaledHeader = false;
/*     */     
/*  98 */     setIndex(12);
/*     */     
/* 100 */     setBufferLength(paramMessage.getSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CorbaConnection getConnection() {
/* 109 */     return this.corbaConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message getMessageHeader() {
/* 118 */     return this.header;
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
/*     */   public void unmarshalHeader() {
/* 131 */     if (!this.unmarshaledHeader) {
/*     */       try {
/* 133 */         if (((ORB)orb()).transportDebugFlag) {
/* 134 */           dprint(".unmarshalHeader->: " + getMessageHeader());
/*     */         }
/* 136 */         getMessageHeader().read((InputStream)this);
/* 137 */         this.unmarshaledHeader = true;
/* 138 */       } catch (RuntimeException runtimeException) {
/* 139 */         if (((ORB)orb()).transportDebugFlag) {
/* 140 */           dprint(".unmarshalHeader: !!ERROR!!: " + 
/* 141 */               getMessageHeader() + ": " + runtimeException);
/*     */         }
/*     */         
/* 144 */         throw runtimeException;
/*     */       } finally {
/* 146 */         if (((ORB)orb()).transportDebugFlag) {
/* 147 */           dprint(".unmarshalHeader<-: " + getMessageHeader());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean unmarshaledHeader() {
/* 155 */     return this.unmarshaledHeader;
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
/*     */   protected CodeSetConversion.BTCConverter createCharBTCConverter() {
/* 168 */     CodeSetComponentInfo.CodeSetContext codeSetContext = getCodeSets();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (codeSetContext == null) {
/* 174 */       return super.createCharBTCConverter();
/*     */     }
/*     */     
/* 177 */     OSFCodeSetRegistry.Entry entry = OSFCodeSetRegistry.lookupEntry(codeSetContext.getCharCodeSet());
/*     */     
/* 179 */     if (entry == null) {
/* 180 */       throw this.wrapper.unknownCodeset(entry);
/*     */     }
/* 182 */     return CodeSetConversion.impl().getBTCConverter(entry, isLittleEndian());
/*     */   }
/*     */ 
/*     */   
/*     */   protected CodeSetConversion.BTCConverter createWCharBTCConverter() {
/* 187 */     CodeSetComponentInfo.CodeSetContext codeSetContext = getCodeSets();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     if (codeSetContext == null) {
/* 193 */       if (getConnection().isServer()) {
/* 194 */         throw this.omgWrapper.noClientWcharCodesetCtx();
/*     */       }
/* 196 */       throw this.omgWrapper.noServerWcharCodesetCmp();
/*     */     } 
/*     */ 
/*     */     
/* 200 */     OSFCodeSetRegistry.Entry entry = OSFCodeSetRegistry.lookupEntry(codeSetContext.getWCharCodeSet());
/*     */     
/* 202 */     if (entry == null) {
/* 203 */       throw this.wrapper.unknownCodeset(entry);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (entry == OSFCodeSetRegistry.UTF_16 && 
/* 214 */       getGIOPVersion().equals(GIOPVersion.V1_2)) {
/* 215 */       return CodeSetConversion.impl().getBTCConverter(entry, false);
/*     */     }
/*     */     
/* 218 */     return CodeSetConversion.impl().getBTCConverter(entry, isLittleEndian());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CodeSetComponentInfo.CodeSetContext getCodeSets() {
/* 227 */     if (getConnection() == null) {
/* 228 */       return CodeSetComponentInfo.LOCAL_CODE_SETS;
/*     */     }
/* 230 */     return getConnection().getCodeSetContext();
/*     */   }
/*     */   
/*     */   public final CodeBase getCodeBase() {
/* 234 */     if (getConnection() == null) {
/* 235 */       return null;
/*     */     }
/* 237 */     return getConnection().getCodeBase();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public CDRInputStream dup() {
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 263 */     ORBUtility.dprint("CDRInputObject", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDRInputObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */