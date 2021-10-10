/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import sun.corba.EncapsInputStreamFactory;
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
/*     */ public class EncapsOutputStream
/*     */   extends CDROutputStream
/*     */ {
/*     */   static final boolean usePooledByteBuffers = false;
/*     */   
/*     */   public EncapsOutputStream(ORB paramORB) {
/*  79 */     this(paramORB, GIOPVersion.V1_2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncapsOutputStream(ORB paramORB, GIOPVersion paramGIOPVersion) {
/*  87 */     this(paramORB, paramGIOPVersion, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EncapsOutputStream(ORB paramORB, boolean paramBoolean) {
/*  93 */     this(paramORB, GIOPVersion.V1_2, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncapsOutputStream(ORB paramORB, GIOPVersion paramGIOPVersion, boolean paramBoolean) {
/* 100 */     super(paramORB, paramGIOPVersion, (byte)0, paramBoolean, 
/* 101 */         BufferManagerFactory.newBufferManagerWrite(0, (byte)0, paramORB), (byte)1, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream create_input_stream() {
/* 110 */     freeInternalCaches();
/*     */     
/* 112 */     return (InputStream)EncapsInputStreamFactory.newEncapsInputStream(orb(), 
/* 113 */         getByteBuffer(), 
/* 114 */         getSize(), 
/* 115 */         isLittleEndian(), 
/* 116 */         getGIOPVersion());
/*     */   }
/*     */   
/*     */   protected CodeSetConversion.CTBConverter createCharCTBConverter() {
/* 120 */     return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.ISO_8859_1);
/*     */   }
/*     */   
/*     */   protected CodeSetConversion.CTBConverter createWCharCTBConverter() {
/* 124 */     if (getGIOPVersion().equals(GIOPVersion.V1_0)) {
/* 125 */       throw this.wrapper.wcharDataInGiop10(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (getGIOPVersion().equals(GIOPVersion.V1_1)) {
/* 130 */       return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.UTF_16, 
/* 131 */           isLittleEndian(), false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     boolean bool = ((ORB)orb()).getORBData().useByteOrderMarkersInEncapsulations();
/*     */     
/* 141 */     return CodeSetConversion.impl().getCTBConverter(OSFCodeSetRegistry.UTF_16, false, bool);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/EncapsOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */