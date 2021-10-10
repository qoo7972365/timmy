/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.IOP.Codec;
/*     */ import org.omg.IOP.CodecFactory;
/*     */ import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
/*     */ import org.omg.IOP.Encoding;
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
/*     */ public final class CodecFactoryImpl
/*     */   extends LocalObject
/*     */   implements CodecFactory
/*     */ {
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private static final int MAX_MINOR_VERSION_SUPPORTED = 2;
/*  59 */   private Codec[] codecs = new Codec[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodecFactoryImpl(ORB paramORB) {
/*  66 */     this.orb = paramORB;
/*  67 */     this.wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.protocol");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     for (byte b = 0; b <= 2; b++) {
/*  77 */       this.codecs[b] = new CDREncapsCodec(paramORB, 1, b);
/*     */     }
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
/*     */   public Codec create_codec(Encoding paramEncoding) throws UnknownEncoding {
/*  92 */     if (paramEncoding == null) nullParam();
/*     */     
/*  94 */     Codec codec = null;
/*     */ 
/*     */     
/*  97 */     if (paramEncoding.format == 0 && paramEncoding.major_version == 1)
/*     */     {
/*     */       
/* 100 */       if (paramEncoding.minor_version >= 0 && paramEncoding.minor_version <= 2)
/*     */       {
/*     */         
/* 103 */         codec = this.codecs[paramEncoding.minor_version];
/*     */       }
/*     */     }
/*     */     
/* 107 */     if (codec == null) {
/* 108 */       throw new UnknownEncoding();
/*     */     }
/*     */     
/* 111 */     return codec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nullParam() {
/* 120 */     throw this.wrapper.nullParam();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/CodecFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */