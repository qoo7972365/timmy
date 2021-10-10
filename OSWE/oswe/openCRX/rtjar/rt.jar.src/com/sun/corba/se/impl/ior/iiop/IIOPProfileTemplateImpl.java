/*     */ package com.sun.corba.se.impl.ior.iiop;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDROutputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.ior.EncapsulationUtility;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfile;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplateBase;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*     */ public class IIOPProfileTemplateImpl
/*     */   extends TaggedProfileTemplateBase
/*     */   implements IIOPProfileTemplate
/*     */ {
/*     */   private ORB orb;
/*     */   private GIOPVersion giopVersion;
/*     */   private IIOPAddress primary;
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  70 */     if (!(paramObject instanceof IIOPProfileTemplateImpl)) {
/*  71 */       return false;
/*     */     }
/*  73 */     IIOPProfileTemplateImpl iIOPProfileTemplateImpl = (IIOPProfileTemplateImpl)paramObject;
/*     */     
/*  75 */     return (super.equals(paramObject) && this.giopVersion.equals(iIOPProfileTemplateImpl.giopVersion) && this.primary
/*  76 */       .equals(iIOPProfileTemplateImpl.primary));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  81 */     return super.hashCode() ^ this.giopVersion.hashCode() ^ this.primary.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public TaggedProfile create(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId) {
/*  86 */     return (TaggedProfile)IIOPFactories.makeIIOPProfile(this.orb, paramObjectKeyTemplate, paramObjectId, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public GIOPVersion getGIOPVersion() {
/*  91 */     return this.giopVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOPAddress getPrimaryAddress() {
/*  96 */     return this.primary;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOPProfileTemplateImpl(ORB paramORB, GIOPVersion paramGIOPVersion, IIOPAddress paramIIOPAddress) {
/* 101 */     this.orb = paramORB;
/* 102 */     this.giopVersion = paramGIOPVersion;
/* 103 */     this.primary = paramIIOPAddress;
/* 104 */     if (this.giopVersion.getMinor() == 0)
/*     */     {
/*     */       
/* 107 */       makeImmutable();
/*     */     }
/*     */   }
/*     */   
/*     */   public IIOPProfileTemplateImpl(InputStream paramInputStream) {
/* 112 */     byte b1 = paramInputStream.read_octet();
/* 113 */     byte b2 = paramInputStream.read_octet();
/* 114 */     this.giopVersion = GIOPVersion.getInstance(b1, b2);
/* 115 */     this.primary = new IIOPAddressImpl(paramInputStream);
/* 116 */     this.orb = (ORB)paramInputStream.orb();
/*     */     
/* 118 */     if (b2 > 0) {
/* 119 */       EncapsulationUtility.readIdentifiableSequence((List)this, (IdentifiableFactoryFinder)this.orb
/* 120 */           .getTaggedComponentFactoryFinder(), paramInputStream);
/*     */     }
/* 122 */     makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId, OutputStream paramOutputStream) {
/* 127 */     this.giopVersion.write((OutputStream)paramOutputStream);
/* 128 */     this.primary.write(paramOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramOutputStream
/* 137 */         .orb(), ((CDROutputStream)paramOutputStream).isLittleEndian());
/*     */     
/* 139 */     paramObjectKeyTemplate.write(paramObjectId, (OutputStream)encapsOutputStream);
/* 140 */     EncapsulationUtility.writeOutputStream((OutputStream)encapsOutputStream, paramOutputStream);
/*     */     
/* 142 */     if (this.giopVersion.getMinor() > 0) {
/* 143 */       EncapsulationUtility.writeIdentifiableSequence((List)this, paramOutputStream);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeContents(OutputStream paramOutputStream) {
/* 150 */     this.giopVersion.write((OutputStream)paramOutputStream);
/* 151 */     this.primary.write(paramOutputStream);
/*     */     
/* 153 */     if (this.giopVersion.getMinor() > 0) {
/* 154 */       EncapsulationUtility.writeIdentifiableSequence((List)this, paramOutputStream);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getId() {
/* 159 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(TaggedProfileTemplate paramTaggedProfileTemplate) {
/* 164 */     if (!(paramTaggedProfileTemplate instanceof IIOPProfileTemplateImpl)) {
/* 165 */       return false;
/*     */     }
/* 167 */     IIOPProfileTemplateImpl iIOPProfileTemplateImpl = (IIOPProfileTemplateImpl)paramTaggedProfileTemplate;
/*     */     
/* 169 */     return this.primary.equals(iIOPProfileTemplateImpl.primary);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/iiop/IIOPProfileTemplateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */