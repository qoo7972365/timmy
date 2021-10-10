/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.logging.IORSystemException;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyFactory;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.IOException;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.OctetSeqHolder;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectKeyFactoryImpl
/*     */   implements ObjectKeyFactory
/*     */ {
/*     */   public static final int MAGIC_BASE = -1347695874;
/*     */   public static final int JAVAMAGIC_OLD = -1347695874;
/*     */   public static final int JAVAMAGIC_NEW = -1347695873;
/*     */   public static final int JAVAMAGIC_NEWER = -1347695872;
/*     */   public static final int MAX_MAGIC = -1347695872;
/*     */   public static final byte JDK1_3_1_01_PATCH_LEVEL = 1;
/*     */   private final ORB orb;
/*     */   private IORSystemException wrapper;
/*     */   private Handler fullKey;
/*     */   private Handler oktempOnly;
/*     */   
/*     */   public ObjectKeyFactoryImpl(ORB paramORB) {
/* 130 */     this.fullKey = new Handler() {
/*     */         public ObjectKeyTemplate handle(int param1Int1, int param1Int2, InputStream param1InputStream, OctetSeqHolder param1OctetSeqHolder) {
/*     */           OldJIDLObjectKeyTemplate oldJIDLObjectKeyTemplate;
/* 133 */           POAObjectKeyTemplate pOAObjectKeyTemplate = null;
/*     */           
/* 135 */           if (param1Int2 >= 32 && param1Int2 <= 63) {
/*     */             
/* 137 */             if (param1Int1 >= -1347695872)
/* 138 */             { pOAObjectKeyTemplate = new POAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream, param1OctetSeqHolder); }
/*     */             else
/* 140 */             { OldPOAObjectKeyTemplate oldPOAObjectKeyTemplate = new OldPOAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream, param1OctetSeqHolder); } 
/* 141 */           } else if (param1Int2 >= 0 && param1Int2 < 32) {
/* 142 */             if (param1Int1 >= -1347695872) {
/* 143 */               JIDLObjectKeyTemplate jIDLObjectKeyTemplate = new JIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream, param1OctetSeqHolder);
/*     */             } else {
/* 145 */               oldJIDLObjectKeyTemplate = new OldJIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream, param1OctetSeqHolder);
/*     */             } 
/*     */           } 
/* 148 */           return oldJIDLObjectKeyTemplate;
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 154 */     this.oktempOnly = new Handler() {
/*     */         public ObjectKeyTemplate handle(int param1Int1, int param1Int2, InputStream param1InputStream, OctetSeqHolder param1OctetSeqHolder) {
/*     */           OldJIDLObjectKeyTemplate oldJIDLObjectKeyTemplate;
/* 157 */           POAObjectKeyTemplate pOAObjectKeyTemplate = null;
/*     */           
/* 159 */           if (param1Int2 >= 32 && param1Int2 <= 63) {
/*     */             
/* 161 */             if (param1Int1 >= -1347695872)
/* 162 */             { pOAObjectKeyTemplate = new POAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream); }
/*     */             else
/* 164 */             { OldPOAObjectKeyTemplate oldPOAObjectKeyTemplate = new OldPOAObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream); } 
/* 165 */           } else if (param1Int2 >= 0 && param1Int2 < 32) {
/* 166 */             if (param1Int1 >= -1347695872) {
/* 167 */               JIDLObjectKeyTemplate jIDLObjectKeyTemplate = new JIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream);
/*     */             } else {
/* 169 */               oldJIDLObjectKeyTemplate = new OldJIDLObjectKeyTemplate(ObjectKeyFactoryImpl.this.orb, param1Int1, param1Int2, param1InputStream);
/*     */             } 
/*     */           } 
/* 172 */           return oldJIDLObjectKeyTemplate;
/*     */         }
/*     */       };
/*     */     this.orb = paramORB;
/*     */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validMagic(int paramInt) {
/* 181 */     return (paramInt >= -1347695874 && paramInt <= -1347695872);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObjectKeyTemplate create(InputStream paramInputStream, Handler paramHandler, OctetSeqHolder paramOctetSeqHolder) {
/* 190 */     ObjectKeyTemplate objectKeyTemplate = null;
/*     */     
/*     */     try {
/* 193 */       paramInputStream.mark(0);
/* 194 */       int i = paramInputStream.read_long();
/*     */       
/* 196 */       if (validMagic(i)) {
/* 197 */         int j = paramInputStream.read_long();
/* 198 */         objectKeyTemplate = paramHandler.handle(i, j, paramInputStream, paramOctetSeqHolder);
/*     */       } 
/* 200 */     } catch (MARSHAL mARSHAL) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (objectKeyTemplate == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 210 */         paramInputStream.reset();
/* 211 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 216 */     return objectKeyTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKey create(byte[] paramArrayOfbyte) {
/* 221 */     OctetSeqHolder octetSeqHolder = new OctetSeqHolder();
/* 222 */     EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream((ORB)this.orb, paramArrayOfbyte, paramArrayOfbyte.length);
/*     */     
/* 224 */     ObjectKeyTemplate objectKeyTemplate = create((InputStream)encapsInputStream, this.fullKey, octetSeqHolder);
/* 225 */     if (objectKeyTemplate == null) {
/* 226 */       objectKeyTemplate = new WireObjectKeyTemplate((InputStream)encapsInputStream, octetSeqHolder);
/*     */     }
/* 228 */     ObjectIdImpl objectIdImpl = new ObjectIdImpl(octetSeqHolder.value);
/* 229 */     return new ObjectKeyImpl(objectKeyTemplate, objectIdImpl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKeyTemplate createTemplate(InputStream paramInputStream) {
/* 234 */     ObjectKeyTemplate objectKeyTemplate = create(paramInputStream, this.oktempOnly, null);
/* 235 */     if (objectKeyTemplate == null) {
/* 236 */       objectKeyTemplate = new WireObjectKeyTemplate(this.orb);
/*     */     }
/* 238 */     return objectKeyTemplate;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectKeyFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */