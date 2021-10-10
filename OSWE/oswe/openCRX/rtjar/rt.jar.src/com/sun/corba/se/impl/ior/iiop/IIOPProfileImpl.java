/*     */ package com.sun.corba.se.impl.ior.iiop;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsInputStream;
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.ior.EncapsulationUtility;
/*     */ import com.sun.corba.se.impl.logging.IORSystemException;
/*     */ import com.sun.corba.se.impl.util.JDKBridge;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableBase;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfile;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.IOP.TaggedProfile;
/*     */ import org.omg.IOP.TaggedProfileHelper;
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
/*     */ public class IIOPProfileImpl
/*     */   extends IdentifiableBase
/*     */   implements IIOPProfile
/*     */ {
/*     */   private ORB orb;
/*     */   private IORSystemException wrapper;
/*     */   private ObjectId oid;
/*     */   private IIOPProfileTemplate proftemp;
/*     */   private ObjectKeyTemplate oktemp;
/*  91 */   protected String codebase = null;
/*     */   
/*     */   protected boolean cachedCodebase = false;
/*     */   private boolean checkedIsLocal = false;
/*     */   private boolean cachedIsLocal = false;
/*     */   
/*     */   private static class LocalCodeBaseSingletonHolder
/*     */   {
/*     */     public static JavaCodebaseComponent comp;
/*     */     
/*     */     static {
/* 102 */       String str = JDKBridge.getLocalCodebase();
/* 103 */       if (str == null) {
/* 104 */         comp = null;
/*     */       } else {
/* 106 */         comp = IIOPFactories.makeJavaCodebaseComponent(str);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 111 */   private GIOPVersion giopVersion = null;
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 115 */     if (!(paramObject instanceof IIOPProfileImpl)) {
/* 116 */       return false;
/*     */     }
/* 118 */     IIOPProfileImpl iIOPProfileImpl = (IIOPProfileImpl)paramObject;
/*     */     
/* 120 */     return (this.oid.equals(iIOPProfileImpl.oid) && this.proftemp.equals(iIOPProfileImpl.proftemp) && this.oktemp
/* 121 */       .equals(iIOPProfileImpl.oktemp));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return this.oid.hashCode() ^ this.proftemp.hashCode() ^ this.oktemp.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectId getObjectId() {
/* 131 */     return this.oid;
/*     */   }
/*     */ 
/*     */   
/*     */   public TaggedProfileTemplate getTaggedProfileTemplate() {
/* 136 */     return (TaggedProfileTemplate)this.proftemp;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKeyTemplate getObjectKeyTemplate() {
/* 141 */     return this.oktemp;
/*     */   }
/*     */ 
/*     */   
/*     */   private IIOPProfileImpl(ORB paramORB) {
/* 146 */     this.orb = paramORB;
/* 147 */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIOPProfileImpl(ORB paramORB, ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId, IIOPProfileTemplate paramIIOPProfileTemplate) {
/* 154 */     this(paramORB);
/* 155 */     this.oktemp = paramObjectKeyTemplate;
/* 156 */     this.oid = paramObjectId;
/* 157 */     this.proftemp = paramIIOPProfileTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOPProfileImpl(InputStream paramInputStream) {
/* 162 */     this((ORB)paramInputStream.orb());
/* 163 */     init(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOPProfileImpl(ORB paramORB, TaggedProfile paramTaggedProfile) {
/* 168 */     this(paramORB);
/*     */     
/* 170 */     if (paramTaggedProfile == null || paramTaggedProfile.tag != 0 || paramTaggedProfile.profile_data == null)
/*     */     {
/* 172 */       throw this.wrapper.invalidTaggedProfile();
/*     */     }
/*     */     
/* 175 */     EncapsInputStream encapsInputStream = EncapsInputStreamFactory.newEncapsInputStream((ORB)paramORB, paramTaggedProfile.profile_data, paramTaggedProfile.profile_data.length);
/*     */     
/* 177 */     encapsInputStream.consumeEndian();
/* 178 */     init((InputStream)encapsInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(InputStream paramInputStream) {
/* 184 */     GIOPVersion gIOPVersion = new GIOPVersion();
/* 185 */     gIOPVersion.read((InputStream)paramInputStream);
/* 186 */     IIOPAddressImpl iIOPAddressImpl = new IIOPAddressImpl(paramInputStream);
/* 187 */     byte[] arrayOfByte = EncapsulationUtility.readOctets(paramInputStream);
/*     */     
/* 189 */     ObjectKey objectKey = this.orb.getObjectKeyFactory().create(arrayOfByte);
/* 190 */     this.oktemp = objectKey.getTemplate();
/* 191 */     this.oid = objectKey.getId();
/*     */     
/* 193 */     this.proftemp = IIOPFactories.makeIIOPProfileTemplate(this.orb, gIOPVersion, iIOPAddressImpl);
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (gIOPVersion.getMinor() > 0) {
/* 198 */       EncapsulationUtility.readIdentifiableSequence((List)this.proftemp, (IdentifiableFactoryFinder)this.orb
/* 199 */           .getTaggedComponentFactoryFinder(), paramInputStream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (uncachedGetCodeBase() == null) {
/* 207 */       JavaCodebaseComponent javaCodebaseComponent = LocalCodeBaseSingletonHolder.comp;
/*     */       
/* 209 */       if (javaCodebaseComponent != null) {
/* 210 */         if (gIOPVersion.getMinor() > 0) {
/* 211 */           this.proftemp.add(javaCodebaseComponent);
/*     */         }
/* 213 */         this.codebase = javaCodebaseComponent.getURLs();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 218 */       this.cachedCodebase = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeContents(OutputStream paramOutputStream) {
/* 224 */     this.proftemp.write(this.oktemp, this.oid, paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 229 */     return this.proftemp.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(TaggedProfile paramTaggedProfile) {
/* 234 */     if (!(paramTaggedProfile instanceof IIOPProfile)) {
/* 235 */       return false;
/*     */     }
/* 237 */     IIOPProfile iIOPProfile = (IIOPProfile)paramTaggedProfile;
/*     */     
/* 239 */     return (this.oid.equals(iIOPProfile.getObjectId()) && this.proftemp
/* 240 */       .isEquivalent(iIOPProfile.getTaggedProfileTemplate()) && this.oktemp
/* 241 */       .equals(iIOPProfile.getObjectKeyTemplate()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKey getObjectKey() {
/* 246 */     return IORFactories.makeObjectKey(this.oktemp, this.oid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaggedProfile getIOPProfile() {
/* 253 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.orb);
/* 254 */     encapsOutputStream.write_long(getId());
/* 255 */     write((OutputStream)encapsOutputStream);
/* 256 */     InputStream inputStream = (InputStream)encapsOutputStream.create_input_stream();
/* 257 */     return TaggedProfileHelper.read((InputStream)inputStream);
/*     */   }
/*     */   
/*     */   private String uncachedGetCodeBase() {
/* 261 */     Iterator<JavaCodebaseComponent> iterator = this.proftemp.iteratorById(25);
/*     */     
/* 263 */     if (iterator.hasNext()) {
/* 264 */       JavaCodebaseComponent javaCodebaseComponent = iterator.next();
/* 265 */       return javaCodebaseComponent.getURLs();
/*     */     } 
/*     */     
/* 268 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized String getCodebase() {
/* 272 */     if (!this.cachedCodebase) {
/* 273 */       this.cachedCodebase = true;
/* 274 */       this.codebase = uncachedGetCodeBase();
/*     */     } 
/*     */     
/* 277 */     return this.codebase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ORBVersion getORBVersion() {
/* 284 */     return this.oktemp.getORBVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isLocal() {
/* 289 */     if (!this.checkedIsLocal) {
/* 290 */       this.checkedIsLocal = true;
/* 291 */       String str = this.proftemp.getPrimaryAddress().getHost();
/*     */       
/* 293 */       this
/*     */ 
/*     */ 
/*     */         
/* 297 */         .cachedIsLocal = (this.orb.isLocalHost(str) && this.orb.isLocalServerId(this.oktemp.getSubcontractId(), this.oktemp.getServerId()) && this.orb.getLegacyServerSocketManager().legacyIsLocalServerPort(this.proftemp
/* 298 */           .getPrimaryAddress().getPort()));
/*     */     } 
/*     */     
/* 301 */     return this.cachedIsLocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getServant() {
/* 312 */     if (!isLocal()) {
/* 313 */       return null;
/*     */     }
/* 315 */     RequestDispatcherRegistry requestDispatcherRegistry = this.orb.getRequestDispatcherRegistry();
/* 316 */     ObjectAdapterFactory objectAdapterFactory = requestDispatcherRegistry.getObjectAdapterFactory(this.oktemp
/* 317 */         .getSubcontractId());
/*     */     
/* 319 */     ObjectAdapterId objectAdapterId = this.oktemp.getObjectAdapterId();
/* 320 */     ObjectAdapter objectAdapter = null;
/*     */     
/*     */     try {
/* 323 */       objectAdapter = objectAdapterFactory.find(objectAdapterId);
/* 324 */     } catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */       
/* 328 */       this.wrapper.getLocalServantFailure((Throwable)systemException, objectAdapterId.toString());
/* 329 */       return null;
/*     */     } 
/*     */     
/* 332 */     byte[] arrayOfByte = this.oid.getId();
/* 333 */     return objectAdapter.getLocalServant(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized GIOPVersion getGIOPVersion() {
/* 344 */     return this.proftemp.getGIOPVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 349 */     this.proftemp.makeImmutable();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/iiop/IIOPProfileImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */