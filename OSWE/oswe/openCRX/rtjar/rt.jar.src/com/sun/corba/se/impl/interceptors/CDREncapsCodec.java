/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.AnyImpl;
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.IOP.Codec;
/*     */ import org.omg.IOP.CodecPackage.FormatMismatch;
/*     */ import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;
/*     */ import org.omg.IOP.CodecPackage.TypeMismatch;
/*     */ import sun.corba.EncapsInputStreamFactory;
/*     */ import sun.corba.OutputStreamFactory;
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
/*     */ public final class CDREncapsCodec
/*     */   extends LocalObject
/*     */   implements Codec
/*     */ {
/*     */   private ORB orb;
/*     */   ORBUtilSystemException wrapper;
/*     */   private GIOPVersion giopVersion;
/*     */   
/*     */   public CDREncapsCodec(ORB paramORB, int paramInt1, int paramInt2) {
/*  80 */     this.orb = paramORB;
/*  81 */     this.wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.protocol");
/*     */ 
/*     */     
/*  84 */     this.giopVersion = GIOPVersion.getInstance((byte)paramInt1, (byte)paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode(Any paramAny) throws InvalidTypeForEncoding {
/*  93 */     if (paramAny == null)
/*  94 */       throw this.wrapper.nullParam(); 
/*  95 */     return encodeImpl(paramAny, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Any decode(byte[] paramArrayOfbyte) throws FormatMismatch {
/* 105 */     if (paramArrayOfbyte == null)
/* 106 */       throw this.wrapper.nullParam(); 
/* 107 */     return decodeImpl(paramArrayOfbyte, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode_value(Any paramAny) throws InvalidTypeForEncoding {
/* 117 */     if (paramAny == null)
/* 118 */       throw this.wrapper.nullParam(); 
/* 119 */     return encodeImpl(paramAny, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Any decode_value(byte[] paramArrayOfbyte, TypeCode paramTypeCode) throws FormatMismatch, TypeMismatch {
/* 130 */     if (paramArrayOfbyte == null)
/* 131 */       throw this.wrapper.nullParam(); 
/* 132 */     if (paramTypeCode == null)
/* 133 */       throw this.wrapper.nullParam(); 
/* 134 */     return decodeImpl(paramArrayOfbyte, paramTypeCode);
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
/*     */   private byte[] encodeImpl(Any paramAny, boolean paramBoolean) throws InvalidTypeForEncoding {
/* 146 */     if (paramAny == null) {
/* 147 */       throw this.wrapper.nullParam();
/*     */     }
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
/* 161 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)this.orb, this.giopVersion);
/*     */ 
/*     */ 
/*     */     
/* 165 */     encapsOutputStream.putEndian();
/*     */ 
/*     */     
/* 168 */     if (paramBoolean) {
/* 169 */       encapsOutputStream.write_TypeCode(paramAny.type());
/*     */     }
/*     */ 
/*     */     
/* 173 */     paramAny.write_value((OutputStream)encapsOutputStream);
/*     */     
/* 175 */     return encapsOutputStream.toByteArray();
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
/*     */   private Any decodeImpl(byte[] paramArrayOfbyte, TypeCode paramTypeCode) throws FormatMismatch {
/* 187 */     if (paramArrayOfbyte == null) {
/* 188 */       throw this.wrapper.nullParam();
/*     */     }
/* 190 */     AnyImpl anyImpl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 198 */       EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream(this.orb, paramArrayOfbyte, paramArrayOfbyte.length, this.giopVersion);
/*     */ 
/*     */ 
/*     */       
/* 202 */       encapsInputStream.consumeEndian();
/*     */ 
/*     */       
/* 205 */       if (paramTypeCode == null) {
/* 206 */         paramTypeCode = encapsInputStream.read_TypeCode();
/*     */       }
/*     */ 
/*     */       
/* 210 */       anyImpl = new AnyImpl((ORB)this.orb);
/* 211 */       anyImpl.read_value((InputStream)encapsInputStream, paramTypeCode);
/*     */     }
/* 213 */     catch (RuntimeException runtimeException) {
/*     */       
/* 215 */       throw new FormatMismatch();
/*     */     } 
/*     */     
/* 218 */     return (Any)anyImpl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/CDREncapsCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */