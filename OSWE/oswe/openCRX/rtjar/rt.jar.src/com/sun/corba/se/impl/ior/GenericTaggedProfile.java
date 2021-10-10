/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfile;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.IOP.TaggedProfile;
/*     */ import org.omg.IOP.TaggedProfileHelper;
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
/*     */ public class GenericTaggedProfile
/*     */   extends GenericIdentifiable
/*     */   implements TaggedProfile
/*     */ {
/*     */   private ORB orb;
/*     */   
/*     */   public GenericTaggedProfile(int paramInt, InputStream paramInputStream) {
/*  51 */     super(paramInt, paramInputStream);
/*  52 */     this.orb = (ORB)paramInputStream.orb();
/*     */   }
/*     */ 
/*     */   
/*     */   public GenericTaggedProfile(ORB paramORB, int paramInt, byte[] paramArrayOfbyte) {
/*  57 */     super(paramInt, paramArrayOfbyte);
/*  58 */     this.orb = paramORB;
/*     */   }
/*     */ 
/*     */   
/*     */   public TaggedProfileTemplate getTaggedProfileTemplate() {
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectId getObjectId() {
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKeyTemplate getObjectKeyTemplate() {
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKey getObjectKey() {
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(TaggedProfile paramTaggedProfile) {
/*  83 */     return equals(paramTaggedProfile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TaggedProfile getIOPProfile() {
/*  99 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.orb);
/* 100 */     write((OutputStream)encapsOutputStream);
/* 101 */     InputStream inputStream = (InputStream)encapsOutputStream.create_input_stream();
/* 102 */     return TaggedProfileHelper.read((InputStream)inputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/GenericTaggedProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */