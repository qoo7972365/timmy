/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.logging.IORSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.HexOutputStream;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.IORTemplateList;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfile;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.IOP.IOR;
/*     */ import org.omg.IOP.IORHelper;
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
/*     */ public class IORImpl
/*     */   extends IdentifiableContainerBase
/*     */   implements IOR
/*     */ {
/*     */   private String typeId;
/*  84 */   private ORB factory = null;
/*     */   
/*     */   private boolean isCachedHashValue = false;
/*     */   private int cachedHashValue;
/*     */   IORSystemException wrapper;
/*     */   
/*     */   public ORB getORB() {
/*  91 */     return this.factory;
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
/* 102 */   private IORTemplateList iortemps = null;
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 106 */     if (paramObject == null) {
/* 107 */       return false;
/*     */     }
/* 109 */     if (!(paramObject instanceof IOR)) {
/* 110 */       return false;
/*     */     }
/* 112 */     IOR iOR = (IOR)paramObject;
/*     */     
/* 114 */     return (super.equals(paramObject) && this.typeId.equals(iOR.getTypeId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int hashCode() {
/* 119 */     if (!this.isCachedHashValue) {
/* 120 */       this.cachedHashValue = super.hashCode() ^ this.typeId.hashCode();
/* 121 */       this.isCachedHashValue = true;
/*     */     } 
/* 123 */     return this.cachedHashValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IORImpl(ORB paramORB) {
/* 130 */     this(paramORB, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public IORImpl(ORB paramORB, String paramString) {
/* 135 */     this.factory = paramORB;
/* 136 */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*     */     
/* 138 */     this.typeId = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IORImpl(ORB paramORB, String paramString, IORTemplate paramIORTemplate, ObjectId paramObjectId) {
/* 146 */     this(paramORB, paramString);
/*     */     
/* 148 */     this.iortemps = IORFactories.makeIORTemplateList();
/* 149 */     this.iortemps.add(paramIORTemplate);
/*     */     
/* 151 */     addTaggedProfiles(paramIORTemplate, paramObjectId);
/*     */     
/* 153 */     makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTaggedProfiles(IORTemplate paramIORTemplate, ObjectId paramObjectId) {
/* 158 */     ObjectKeyTemplate objectKeyTemplate = paramIORTemplate.getObjectKeyTemplate();
/* 159 */     Iterator<TaggedProfileTemplate> iterator = paramIORTemplate.iterator();
/*     */     
/* 161 */     while (iterator.hasNext()) {
/*     */       
/* 163 */       TaggedProfileTemplate taggedProfileTemplate = iterator.next();
/*     */       
/* 165 */       TaggedProfile taggedProfile = taggedProfileTemplate.create(objectKeyTemplate, paramObjectId);
/*     */       
/* 167 */       add(taggedProfile);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IORImpl(ORB paramORB, String paramString, IORTemplateList paramIORTemplateList, ObjectId paramObjectId) {
/* 176 */     this(paramORB, paramString);
/*     */     
/* 178 */     this.iortemps = paramIORTemplateList;
/*     */     
/* 180 */     Iterator<IORTemplate> iterator = paramIORTemplateList.iterator();
/* 181 */     while (iterator.hasNext()) {
/* 182 */       IORTemplate iORTemplate = iterator.next();
/* 183 */       addTaggedProfiles(iORTemplate, paramObjectId);
/*     */     } 
/*     */     
/* 186 */     makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public IORImpl(InputStream paramInputStream) {
/* 191 */     this((ORB)paramInputStream.orb(), paramInputStream.read_string());
/*     */ 
/*     */     
/* 194 */     IdentifiableFactoryFinder identifiableFactoryFinder = this.factory.getTaggedProfileFactoryFinder();
/*     */     
/* 196 */     EncapsulationUtility.readIdentifiableSequence((List)this, identifiableFactoryFinder, paramInputStream);
/*     */     
/* 198 */     makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTypeId() {
/* 203 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 208 */     paramOutputStream.write_string(this.typeId);
/* 209 */     EncapsulationUtility.writeIdentifiableSequence((List)this, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String stringify() {
/* 217 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.factory);
/* 218 */     encapsOutputStream.putEndian();
/* 219 */     write((OutputStream)encapsOutputStream);
/* 220 */     StringWriter stringWriter = new StringWriter();
/*     */     try {
/* 222 */       encapsOutputStream.writeTo((OutputStream)new HexOutputStream(stringWriter));
/* 223 */     } catch (IOException iOException) {
/* 224 */       throw this.wrapper.stringifyWriteError(iOException);
/*     */     } 
/*     */     
/* 227 */     return "IOR:" + stringWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void makeImmutable() {
/* 232 */     makeElementsImmutable();
/*     */     
/* 234 */     if (this.iortemps != null) {
/* 235 */       this.iortemps.makeImmutable();
/*     */     }
/* 237 */     super.makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public IOR getIOPIOR() {
/* 242 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.factory);
/* 243 */     write((OutputStream)encapsOutputStream);
/* 244 */     InputStream inputStream = (InputStream)encapsOutputStream.create_input_stream();
/* 245 */     return IORHelper.read((InputStream)inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNil() {
/* 255 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(IOR paramIOR) {
/* 260 */     Iterator<TaggedProfile> iterator1 = iterator();
/* 261 */     Iterator<TaggedProfile> iterator2 = paramIOR.iterator();
/* 262 */     while (iterator1.hasNext() && iterator2.hasNext()) {
/* 263 */       TaggedProfile taggedProfile1 = iterator1.next();
/* 264 */       TaggedProfile taggedProfile2 = iterator2.next();
/* 265 */       if (!taggedProfile1.isEquivalent(taggedProfile2)) {
/* 266 */         return false;
/*     */       }
/*     */     } 
/* 269 */     return (iterator1.hasNext() == iterator2.hasNext());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeIORTemplateList() {
/* 275 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 277 */     this.iortemps = IORFactories.makeIORTemplateList();
/* 278 */     Iterator<TaggedProfile> iterator = iterator();
/* 279 */     ObjectId objectId = null;
/* 280 */     while (iterator.hasNext()) {
/* 281 */       TaggedProfile taggedProfile = iterator.next();
/* 282 */       TaggedProfileTemplate taggedProfileTemplate = taggedProfile.getTaggedProfileTemplate();
/* 283 */       ObjectKeyTemplate objectKeyTemplate = taggedProfile.getObjectKeyTemplate();
/*     */ 
/*     */ 
/*     */       
/* 287 */       if (objectId == null) {
/* 288 */         objectId = taggedProfile.getObjectId();
/* 289 */       } else if (!objectId.equals(taggedProfile.getObjectId())) {
/* 290 */         throw this.wrapper.badOidInIorTemplateList();
/*     */       } 
/*     */       
/* 293 */       IORTemplate iORTemplate = (IORTemplate)hashMap.get(objectKeyTemplate);
/* 294 */       if (iORTemplate == null) {
/* 295 */         iORTemplate = IORFactories.makeIORTemplate(objectKeyTemplate);
/* 296 */         hashMap.put(objectKeyTemplate, iORTemplate);
/* 297 */         this.iortemps.add(iORTemplate);
/*     */       } 
/*     */       
/* 300 */       iORTemplate.add(taggedProfileTemplate);
/*     */     } 
/*     */     
/* 303 */     this.iortemps.makeImmutable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized IORTemplateList getIORTemplates() {
/* 314 */     if (this.iortemps == null) {
/* 315 */       initializeIORTemplateList();
/*     */     }
/* 317 */     return this.iortemps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIOPProfile getProfile() {
/* 326 */     IIOPProfile iIOPProfile = null;
/* 327 */     Iterator<IIOPProfile> iterator = iteratorById(0);
/* 328 */     if (iterator.hasNext()) {
/* 329 */       iIOPProfile = iterator.next();
/*     */     }
/* 331 */     if (iIOPProfile != null) {
/* 332 */       return iIOPProfile;
/*     */     }
/*     */ 
/*     */     
/* 336 */     throw this.wrapper.iorMustHaveIiopProfile();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/IORImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */