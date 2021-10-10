/*     */ package com.sun.corba.se.impl.oa.toa;
/*     */ 
/*     */ import com.sun.corba.se.impl.ior.JIDLObjectKeyTemplate;
/*     */ import com.sun.corba.se.impl.oa.NullServantImpl;
/*     */ import com.sun.corba.se.impl.oa.poa.Policies;
/*     */ import com.sun.corba.se.impl.protocol.JIDLLocalCRDImpl;
/*     */ import com.sun.corba.se.pept.protocol.ClientDelegate;
/*     */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*     */ import com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.oa.OADestroyed;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterBase;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TOAImpl
/*     */   extends ObjectAdapterBase
/*     */   implements TOA
/*     */ {
/*     */   private TransientObjectManager servants;
/*     */   
/*     */   public TOAImpl(ORB paramORB, TransientObjectManager paramTransientObjectManager, String paramString) {
/*  83 */     super(paramORB);
/*  84 */     this.servants = paramTransientObjectManager;
/*     */ 
/*     */     
/*  87 */     int i = getORB().getTransientServerId();
/*  88 */     byte b = 2;
/*     */     
/*  90 */     JIDLObjectKeyTemplate jIDLObjectKeyTemplate = new JIDLObjectKeyTemplate(paramORB, b, i);
/*     */ 
/*     */     
/*  93 */     Policies policies = Policies.defaultPolicies;
/*     */ 
/*     */     
/*  96 */     initializeTemplate((ObjectKeyTemplate)jIDLObjectKeyTemplate, true, policies, paramString, null, jIDLObjectKeyTemplate
/*     */ 
/*     */ 
/*     */         
/* 100 */         .getObjectAdapterId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectCopierFactory getObjectCopierFactory() {
/* 108 */     CopierManager copierManager = getORB().getCopierManager();
/* 109 */     return copierManager.getDefaultObjectCopierFactory();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getLocalServant(byte[] paramArrayOfbyte) {
/* 114 */     return (Object)this.servants.lookupServant(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getInvocationServant(OAInvocationInfo paramOAInvocationInfo) {
/* 125 */     Object object = this.servants.lookupServant(paramOAInvocationInfo.id());
/* 126 */     if (object == null)
/*     */     {
/*     */       
/* 129 */       object = new NullServantImpl((SystemException)lifecycleWrapper().nullServant()); } 
/* 130 */     paramOAInvocationInfo.setServant(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnServant() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getInterfaces(Object paramObject, byte[] paramArrayOfbyte) {
/* 142 */     return StubAdapter.getTypeIds(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Policy getEffectivePolicy(int paramInt) {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getManagerId() {
/* 156 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getState() {
/* 161 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enter() throws OADestroyed {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(Object paramObject) {
/* 178 */     byte[] arrayOfByte = this.servants.storeServant(paramObject, null);
/*     */ 
/*     */     
/* 181 */     String str = StubAdapter.getTypeIds(paramObject)[0];
/*     */ 
/*     */     
/* 184 */     ObjectReferenceFactory objectReferenceFactory = getCurrentFactory();
/* 185 */     Object object = objectReferenceFactory.make_object(str, arrayOfByte);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     Delegate delegate = StubAdapter.getDelegate(object);
/*     */ 
/*     */     
/* 193 */     CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)((ClientDelegate)delegate).getContactInfoList();
/*     */     
/* 195 */     LocalClientRequestDispatcher localClientRequestDispatcher = corbaContactInfoList.getLocalClientRequestDispatcher();
/*     */     
/* 197 */     if (localClientRequestDispatcher instanceof JIDLLocalCRDImpl) {
/* 198 */       JIDLLocalCRDImpl jIDLLocalCRDImpl = (JIDLLocalCRDImpl)localClientRequestDispatcher;
/* 199 */       jIDLLocalCRDImpl.setServant(paramObject);
/*     */     } else {
/* 201 */       throw new RuntimeException("TOAImpl.connect can not be called on " + localClientRequestDispatcher);
/*     */     } 
/*     */ 
/*     */     
/* 205 */     StubAdapter.setDelegate(paramObject, delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(Object paramObject) {
/* 211 */     Delegate delegate = StubAdapter.getDelegate(paramObject);
/*     */ 
/*     */     
/* 214 */     CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)((ClientDelegate)delegate).getContactInfoList();
/*     */     
/* 216 */     LocalClientRequestDispatcher localClientRequestDispatcher = corbaContactInfoList.getLocalClientRequestDispatcher();
/*     */     
/* 218 */     if (localClientRequestDispatcher instanceof JIDLLocalCRDImpl) {
/* 219 */       JIDLLocalCRDImpl jIDLLocalCRDImpl = (JIDLLocalCRDImpl)localClientRequestDispatcher;
/* 220 */       byte[] arrayOfByte = jIDLLocalCRDImpl.getObjectId();
/* 221 */       this.servants.deleteServant(arrayOfByte);
/* 222 */       jIDLLocalCRDImpl.unexport();
/*     */     } else {
/* 224 */       throw new RuntimeException("TOAImpl.disconnect can not be called on " + localClientRequestDispatcher);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/toa/TOAImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */