/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapterFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.closure.Closure;
/*     */ import com.sun.corba.se.spi.orbutil.closure.ClosureFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import org.omg.CORBA.OBJECT_NOT_EXIST;
/*     */ import org.omg.CORBA.ORBPackage.InvalidName;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TRANSIENT;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAManager;
/*     */ import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
/*     */ import org.omg.PortableServer.POAPackage.AdapterNonExistent;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.portable.Delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POAFactory
/*     */   implements ObjectAdapterFactory
/*     */ {
/*  69 */   private Map exportedServantsToPOA = new WeakHashMap<>();
/*     */   
/*     */   private Set poaManagers;
/*     */   
/*     */   private int poaManagerId;
/*     */   private int poaId;
/*     */   private POAImpl rootPOA;
/*     */   private DelegateImpl delegateImpl;
/*     */   private ORB orb;
/*     */   private POASystemException wrapper;
/*     */   private OMGSystemException omgWrapper;
/*     */   private boolean isShuttingDown = false;
/*     */   
/*     */   public POASystemException getWrapper() {
/*  83 */     return this.wrapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public POAFactory() {
/*  90 */     this.poaManagers = Collections.synchronizedSet(new HashSet(4));
/*  91 */     this.poaManagerId = 0;
/*  92 */     this.poaId = 0;
/*  93 */     this.rootPOA = null;
/*  94 */     this.delegateImpl = null;
/*  95 */     this.orb = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized POA lookupPOA(Servant paramServant) {
/* 100 */     return (POA)this.exportedServantsToPOA.get(paramServant);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void registerPOAForServant(POA paramPOA, Servant paramServant) {
/* 105 */     this.exportedServantsToPOA.put(paramServant, paramPOA);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void unregisterPOAForServant(POA paramPOA, Servant paramServant) {
/* 110 */     this.exportedServantsToPOA.remove(paramServant);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(ORB paramORB) {
/* 117 */     this.orb = paramORB;
/* 118 */     this.wrapper = POASystemException.get(paramORB, "oa.lifecycle");
/*     */     
/* 120 */     this.omgWrapper = OMGSystemException.get(paramORB, "oa.lifecycle");
/*     */     
/* 122 */     this.delegateImpl = new DelegateImpl(paramORB, this);
/* 123 */     registerRootPOA();
/*     */     
/* 125 */     POACurrent pOACurrent = new POACurrent(paramORB);
/* 126 */     paramORB.getLocalResolver().register("POACurrent", 
/* 127 */         ClosureFactory.makeConstant(pOACurrent));
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectAdapter find(ObjectAdapterId paramObjectAdapterId) {
/* 132 */     POA pOA = null;
/*     */     try {
/* 134 */       boolean bool = true;
/* 135 */       Iterator<String> iterator = paramObjectAdapterId.iterator();
/* 136 */       pOA = getRootPOA();
/* 137 */       while (iterator.hasNext()) {
/* 138 */         String str = iterator.next();
/*     */         
/* 140 */         if (bool) {
/* 141 */           if (!str.equals("RootPOA"))
/* 142 */             throw this.wrapper.makeFactoryNotPoa(str); 
/* 143 */           bool = false; continue;
/*     */         } 
/* 145 */         pOA = pOA.find_POA(str, true);
/*     */       }
/*     */     
/* 148 */     } catch (AdapterNonExistent adapterNonExistent) {
/* 149 */       throw this.omgWrapper.noObjectAdaptor(adapterNonExistent);
/* 150 */     } catch (OBJECT_NOT_EXIST oBJECT_NOT_EXIST) {
/* 151 */       throw oBJECT_NOT_EXIST;
/* 152 */     } catch (TRANSIENT tRANSIENT) {
/* 153 */       throw tRANSIENT;
/* 154 */     } catch (Exception exception) {
/* 155 */       throw this.wrapper.poaLookupError(exception);
/*     */     } 
/*     */     
/* 158 */     if (pOA == null) {
/* 159 */       throw this.wrapper.poaLookupError();
/*     */     }
/* 161 */     return (ObjectAdapter)pOA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown(boolean paramBoolean) {
/* 168 */     Iterator<?> iterator = null;
/* 169 */     synchronized (this) {
/* 170 */       this.isShuttingDown = true;
/* 171 */       iterator = (new HashSet(this.poaManagers)).iterator();
/*     */     } 
/*     */     
/* 174 */     while (iterator.hasNext()) {
/*     */       try {
/* 176 */         ((POAManager)iterator.next()).deactivate(true, paramBoolean);
/* 177 */       } catch (AdapterInactive adapterInactive) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removePoaManager(POAManager paramPOAManager) {
/* 185 */     this.poaManagers.remove(paramPOAManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addPoaManager(POAManager paramPOAManager) {
/* 190 */     this.poaManagers.add(paramPOAManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int newPOAManagerId() {
/* 195 */     return this.poaManagerId++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRootPOA() {
/* 203 */     Closure closure = new Closure() {
/*     */         public Object evaluate() {
/* 205 */           return POAImpl.makeRootPOA(POAFactory.this.orb);
/*     */         }
/*     */       };
/*     */     
/* 209 */     this.orb.getLocalResolver().register("RootPOA", 
/* 210 */         ClosureFactory.makeFuture(closure));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized POA getRootPOA() {
/* 216 */     if (this.rootPOA == null) {
/*     */       
/* 218 */       if (this.isShuttingDown) {
/* 219 */         throw this.omgWrapper.noObjectAdaptor();
/*     */       }
/*     */       
/*     */       try {
/* 223 */         Object object = this.orb.resolve_initial_references("RootPOA");
/*     */         
/* 225 */         this.rootPOA = (POAImpl)object;
/* 226 */       } catch (InvalidName invalidName) {
/* 227 */         throw this.wrapper.cantResolveRootPoa(invalidName);
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     return this.rootPOA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Delegate getDelegateImpl() {
/* 236 */     return this.delegateImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int newPOAId() {
/* 241 */     return this.poaId++;
/*     */   }
/*     */ 
/*     */   
/*     */   public ORB getORB() {
/* 246 */     return this.orb;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */