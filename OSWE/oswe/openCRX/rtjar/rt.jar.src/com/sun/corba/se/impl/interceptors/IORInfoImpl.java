/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponent;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.legacy.interceptor.IORInfoExt;
/*     */ import com.sun.corba.se.spi.legacy.interceptor.UnknownType;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.IOP.TaggedComponent;
/*     */ import org.omg.PortableInterceptor.IORInfo;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceFactory;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IORInfoImpl
/*     */   extends LocalObject
/*     */   implements IORInfo, IORInfoExt
/*     */ {
/*     */   private static final int STATE_INITIAL = 0;
/*     */   private static final int STATE_ESTABLISHED = 1;
/*     */   private static final int STATE_DONE = 2;
/*  83 */   private int state = 0;
/*     */ 
/*     */   
/*     */   private ObjectAdapter adapter;
/*     */ 
/*     */   
/*     */   private ORB orb;
/*     */   
/*     */   private ORBUtilSystemException orbutilWrapper;
/*     */   
/*     */   private InterceptorsSystemException wrapper;
/*     */   
/*     */   private OMGSystemException omgWrapper;
/*     */ 
/*     */   
/*     */   IORInfoImpl(ObjectAdapter paramObjectAdapter) {
/*  99 */     this.orb = paramObjectAdapter.getORB();
/*     */     
/* 101 */     this.orbutilWrapper = ORBUtilSystemException.get(this.orb, "rpc.protocol");
/*     */     
/* 103 */     this.wrapper = InterceptorsSystemException.get(this.orb, "rpc.protocol");
/*     */     
/* 105 */     this.omgWrapper = OMGSystemException.get(this.orb, "rpc.protocol");
/*     */ 
/*     */     
/* 108 */     this.adapter = paramObjectAdapter;
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
/*     */   
/*     */   public Policy get_effective_policy(int paramInt) {
/* 129 */     checkState(0, 1);
/*     */     
/* 131 */     return this.adapter.getEffectivePolicy(paramInt);
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
/*     */   public void add_ior_component(TaggedComponent paramTaggedComponent) {
/* 145 */     checkState(0);
/*     */     
/* 147 */     if (paramTaggedComponent == null) nullParam(); 
/* 148 */     addIORComponentToProfileInternal(paramTaggedComponent, this.adapter
/* 149 */         .getIORTemplate().iterator());
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
/*     */ 
/*     */   
/*     */   public void add_ior_component_to_profile(TaggedComponent paramTaggedComponent, int paramInt) {
/* 171 */     checkState(0);
/*     */     
/* 173 */     if (paramTaggedComponent == null) nullParam(); 
/* 174 */     addIORComponentToProfileInternal(paramTaggedComponent, this.adapter
/* 175 */         .getIORTemplate().iteratorById(paramInt));
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
/*     */   public int getServerPort(String paramString) throws UnknownType {
/* 188 */     checkState(0, 1);
/*     */ 
/*     */ 
/*     */     
/* 192 */     int i = this.orb.getLegacyServerSocketManager().legacyGetTransientOrPersistentServerPort(paramString);
/* 193 */     if (i == -1) {
/* 194 */       throw new UnknownType();
/*     */     }
/* 196 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectAdapter getObjectAdapter() {
/* 201 */     return this.adapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public int manager_id() {
/* 206 */     checkState(0, 1);
/*     */     
/* 208 */     return this.adapter.getManagerId();
/*     */   }
/*     */ 
/*     */   
/*     */   public short state() {
/* 213 */     checkState(0, 1);
/*     */     
/* 215 */     return this.adapter.getState();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectReferenceTemplate adapter_template() {
/* 220 */     checkState(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     return this.adapter.getAdapterTemplate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectReferenceFactory current_factory() {
/* 239 */     checkState(1);
/*     */     
/* 241 */     return this.adapter.getCurrentFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   public void current_factory(ObjectReferenceFactory paramObjectReferenceFactory) {
/* 246 */     checkState(1);
/*     */     
/* 248 */     this.adapter.setCurrentFactory(paramObjectReferenceFactory);
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
/*     */   private void addIORComponentToProfileInternal(TaggedComponent paramTaggedComponent, Iterator<TaggedProfileTemplate> paramIterator) {
/* 261 */     TaggedComponentFactoryFinder taggedComponentFactoryFinder = this.orb.getTaggedComponentFactoryFinder();
/* 262 */     TaggedComponent taggedComponent = taggedComponentFactoryFinder.create((ORB)this.orb, paramTaggedComponent);
/*     */ 
/*     */ 
/*     */     
/* 266 */     boolean bool = false;
/* 267 */     while (paramIterator.hasNext()) {
/* 268 */       bool = true;
/*     */       
/* 270 */       TaggedProfileTemplate taggedProfileTemplate = paramIterator.next();
/* 271 */       taggedProfileTemplate.add(taggedComponent);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 276 */     if (!bool) {
/* 277 */       throw this.omgWrapper.invalidProfileId();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nullParam() {
/* 287 */     throw this.orbutilWrapper.nullParam();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkState(int paramInt) {
/* 294 */     if (paramInt != this.state) {
/* 295 */       throw this.wrapper.badState1(new Integer(paramInt), new Integer(this.state));
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkState(int paramInt1, int paramInt2) {
/* 300 */     if (paramInt1 != this.state && paramInt2 != this.state) {
/* 301 */       throw this.wrapper.badState2(new Integer(paramInt1), new Integer(paramInt2), new Integer(this.state));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void makeStateEstablished() {
/* 307 */     checkState(0);
/*     */     
/* 309 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   void makeStateDone() {
/* 314 */     checkState(1);
/*     */     
/* 316 */     this.state = 2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/IORInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */