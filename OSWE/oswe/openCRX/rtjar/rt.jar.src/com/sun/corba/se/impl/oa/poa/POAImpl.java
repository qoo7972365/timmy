/*      */ package com.sun.corba.se.impl.oa.poa;
/*      */ 
/*      */ import com.sun.corba.se.impl.ior.ObjectAdapterIdArray;
/*      */ import com.sun.corba.se.impl.ior.POAObjectKeyTemplate;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.orbutil.concurrent.CondVar;
/*      */ import com.sun.corba.se.impl.orbutil.concurrent.ReentrantMutex;
/*      */ import com.sun.corba.se.impl.orbutil.concurrent.Sync;
/*      */ import com.sun.corba.se.impl.orbutil.concurrent.SyncUtil;
/*      */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*      */ import com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.IORFactories;
/*      */ import com.sun.corba.se.spi.ior.IORFactory;
/*      */ import com.sun.corba.se.spi.ior.IORTemplateList;
/*      */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*      */ import com.sun.corba.se.spi.ior.ObjectId;
/*      */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*      */ import com.sun.corba.se.spi.ior.TaggedProfile;
/*      */ import com.sun.corba.se.spi.oa.OADestroyed;
/*      */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*      */ import com.sun.corba.se.spi.oa.ObjectAdapterBase;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.protocol.ForwardException;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.Policy;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.PortableInterceptor.ObjectReferenceFactory;
/*      */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
/*      */ import org.omg.PortableServer.AdapterActivator;
/*      */ import org.omg.PortableServer.ForwardRequest;
/*      */ import org.omg.PortableServer.IdAssignmentPolicy;
/*      */ import org.omg.PortableServer.IdAssignmentPolicyValue;
/*      */ import org.omg.PortableServer.IdUniquenessPolicy;
/*      */ import org.omg.PortableServer.IdUniquenessPolicyValue;
/*      */ import org.omg.PortableServer.ImplicitActivationPolicy;
/*      */ import org.omg.PortableServer.ImplicitActivationPolicyValue;
/*      */ import org.omg.PortableServer.LifespanPolicy;
/*      */ import org.omg.PortableServer.LifespanPolicyValue;
/*      */ import org.omg.PortableServer.POA;
/*      */ import org.omg.PortableServer.POAManager;
/*      */ import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
/*      */ import org.omg.PortableServer.POAPackage.AdapterNonExistent;
/*      */ import org.omg.PortableServer.POAPackage.InvalidPolicy;
/*      */ import org.omg.PortableServer.POAPackage.NoServant;
/*      */ import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
/*      */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*      */ import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
/*      */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*      */ import org.omg.PortableServer.POAPackage.WrongAdapter;
/*      */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*      */ import org.omg.PortableServer.RequestProcessingPolicy;
/*      */ import org.omg.PortableServer.RequestProcessingPolicyValue;
/*      */ import org.omg.PortableServer.Servant;
/*      */ import org.omg.PortableServer.ServantManager;
/*      */ import org.omg.PortableServer.ServantRetentionPolicy;
/*      */ import org.omg.PortableServer.ServantRetentionPolicyValue;
/*      */ import org.omg.PortableServer.ThreadPolicy;
/*      */ import org.omg.PortableServer.ThreadPolicyValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class POAImpl
/*      */   extends ObjectAdapterBase
/*      */   implements POA
/*      */ {
/*      */   private boolean debug;
/*      */   private static final int STATE_START = 0;
/*      */   private static final int STATE_INIT = 1;
/*      */   private static final int STATE_INIT_DONE = 2;
/*      */   private static final int STATE_RUN = 3;
/*      */   private static final int STATE_DESTROYING = 4;
/*      */   private static final int STATE_DESTROYED = 5;
/*      */   private int state;
/*      */   private POAPolicyMediator mediator;
/*      */   private int numLevels;
/*      */   private ObjectAdapterId poaId;
/*      */   private String name;
/*      */   private POAManagerImpl manager;
/*      */   private int uniquePOAId;
/*      */   private POAImpl parent;
/*      */   private Map children;
/*      */   private AdapterActivator activator;
/*      */   private int invocationCount;
/*      */   Sync poaMutex;
/*      */   private CondVar adapterActivatorCV;
/*      */   private CondVar invokeCV;
/*      */   private CondVar beingDestroyedCV;
/*      */   protected ThreadLocal isDestroying;
/*      */   
/*      */   private String stateToString() {
/*  179 */     switch (this.state) {
/*      */       case 0:
/*  181 */         return "START";
/*      */       case 1:
/*  183 */         return "INIT";
/*      */       case 2:
/*  185 */         return "INIT_DONE";
/*      */       case 3:
/*  187 */         return "RUN";
/*      */       case 4:
/*  189 */         return "DESTROYING";
/*      */       case 5:
/*  191 */         return "DESTROYED";
/*      */     } 
/*  193 */     return "UNKNOWN(" + this.state + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  245 */     return "POA[" + this.poaId.toString() + ", uniquePOAId=" + this.uniquePOAId + ", state=" + 
/*      */       
/*  247 */       stateToString() + ", invocationCount=" + this.invocationCount + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean getDebug() {
/*  254 */     return this.debug;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static POAFactory getPOAFactory(ORB paramORB) {
/*  260 */     return (POAFactory)paramORB.getRequestDispatcherRegistry()
/*  261 */       .getObjectAdapterFactory(32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static POAImpl makeRootPOA(ORB paramORB) {
/*  268 */     POAManagerImpl pOAManagerImpl = new POAManagerImpl(getPOAFactory(paramORB), paramORB.getPIHandler());
/*      */     
/*  270 */     POAImpl pOAImpl = new POAImpl("RootPOA", null, paramORB, 0);
/*      */     
/*  272 */     pOAImpl.initialize(pOAManagerImpl, Policies.rootPOAPolicies);
/*      */     
/*  274 */     return pOAImpl;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getPOAId() {
/*  280 */     return this.uniquePOAId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void lock() {
/*  287 */     SyncUtil.acquire(this.poaMutex);
/*      */     
/*  289 */     if (this.debug) {
/*  290 */       ORBUtility.dprint(this, "LOCKED poa " + this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void unlock() {
/*  297 */     if (this.debug) {
/*  298 */       ORBUtility.dprint(this, "UNLOCKED poa " + this);
/*      */     }
/*      */     
/*  301 */     this.poaMutex.release();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Policies getPolicies() {
/*  307 */     return this.mediator.getPolicies();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private POAImpl(String paramString, POAImpl paramPOAImpl, ORB paramORB, int paramInt) {
/*  313 */     super(paramORB);
/*      */     
/*  315 */     this.debug = paramORB.poaDebugFlag;
/*      */     
/*  317 */     if (this.debug) {
/*  318 */       ORBUtility.dprint(this, "Creating POA with name=" + paramString + " parent=" + paramPOAImpl);
/*      */     }
/*      */ 
/*      */     
/*  322 */     this.state = paramInt;
/*  323 */     this.name = paramString;
/*  324 */     this.parent = paramPOAImpl;
/*  325 */     this.children = new HashMap<>();
/*  326 */     this.activator = null;
/*      */ 
/*      */ 
/*      */     
/*  330 */     this.uniquePOAId = getPOAFactory(paramORB).newPOAId();
/*      */     
/*  332 */     if (paramPOAImpl == null) {
/*      */       
/*  334 */       this.numLevels = 1;
/*      */     } else {
/*      */       
/*  337 */       paramPOAImpl.numLevels++;
/*      */       
/*  339 */       paramPOAImpl.children.put(paramString, this);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  344 */     String[] arrayOfString = new String[this.numLevels];
/*  345 */     POAImpl pOAImpl = this;
/*  346 */     int i = this.numLevels - 1;
/*  347 */     while (pOAImpl != null) {
/*  348 */       arrayOfString[i--] = pOAImpl.name;
/*  349 */       pOAImpl = pOAImpl.parent;
/*      */     } 
/*      */     
/*  352 */     this.poaId = (ObjectAdapterId)new ObjectAdapterIdArray(arrayOfString);
/*      */     
/*  354 */     this.invocationCount = 0;
/*      */     
/*  356 */     this.poaMutex = (Sync)new ReentrantMutex(paramORB.poaConcurrencyDebugFlag);
/*      */     
/*  358 */     this.adapterActivatorCV = new CondVar(this.poaMutex, paramORB.poaConcurrencyDebugFlag);
/*      */     
/*  360 */     this.invokeCV = new CondVar(this.poaMutex, paramORB.poaConcurrencyDebugFlag);
/*      */     
/*  362 */     this.beingDestroyedCV = new CondVar(this.poaMutex, paramORB.poaConcurrencyDebugFlag);
/*      */ 
/*      */     
/*  365 */     this.isDestroying = new ThreadLocal() {
/*      */         protected Object initialValue() {
/*  367 */           return Boolean.FALSE;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(POAManagerImpl paramPOAManagerImpl, Policies paramPolicies) {
/*  375 */     if (this.debug) {
/*  376 */       ORBUtility.dprint(this, "Initializing poa " + this + " with POAManager=" + paramPOAManagerImpl + " policies=" + paramPolicies);
/*      */     }
/*      */ 
/*      */     
/*  380 */     this.manager = paramPOAManagerImpl;
/*  381 */     paramPOAManagerImpl.addPOA(this);
/*      */     
/*  383 */     this.mediator = POAPolicyMediatorFactory.create(paramPolicies, this);
/*      */ 
/*      */     
/*  386 */     int i = this.mediator.getServerId();
/*  387 */     int j = this.mediator.getScid();
/*  388 */     String str = getORB().getORBData().getORBId();
/*      */     
/*  390 */     POAObjectKeyTemplate pOAObjectKeyTemplate = new POAObjectKeyTemplate(getORB(), j, i, str, this.poaId);
/*      */ 
/*      */     
/*  393 */     if (this.debug) {
/*  394 */       ORBUtility.dprint(this, "Initializing poa: oktemp=" + pOAObjectKeyTemplate);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  400 */     boolean bool = true;
/*      */ 
/*      */ 
/*      */     
/*  404 */     initializeTemplate((ObjectKeyTemplate)pOAObjectKeyTemplate, bool, paramPolicies, null, null, pOAObjectKeyTemplate
/*      */ 
/*      */ 
/*      */         
/*  408 */         .getObjectAdapterId());
/*      */ 
/*      */     
/*  411 */     if (this.state == 0) {
/*  412 */       this.state = 3;
/*  413 */     } else if (this.state == 1) {
/*  414 */       this.state = 2;
/*      */     } else {
/*  416 */       throw lifecycleWrapper().illegalPoaStateTrans();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean waitUntilRunning() {
/*  422 */     if (this.debug) {
/*  423 */       ORBUtility.dprint(this, "Calling waitUntilRunning on poa " + this);
/*      */     }
/*      */ 
/*      */     
/*  427 */     while (this.state < 3) {
/*      */       try {
/*  429 */         this.adapterActivatorCV.await();
/*  430 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  435 */     if (this.debug) {
/*  436 */       ORBUtility.dprint(this, "Exiting waitUntilRunning on poa " + this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  442 */     return (this.state == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean destroyIfNotInitDone() {
/*      */     try {
/*  459 */       lock();
/*      */       
/*  461 */       if (this.debug) {
/*  462 */         ORBUtility.dprint(this, "Calling destroyIfNotInitDone on poa " + this);
/*      */       }
/*      */ 
/*      */       
/*  466 */       boolean bool = (this.state == 2) ? true : false;
/*      */       
/*  468 */       if (bool) {
/*  469 */         this.state = 3;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  474 */         DestroyThread destroyThread = new DestroyThread(false, this.debug);
/*  475 */         destroyThread.doIt(this, true);
/*      */       } 
/*      */       
/*  478 */       return bool;
/*      */     } finally {
/*  480 */       this.adapterActivatorCV.broadcast();
/*      */       
/*  482 */       if (this.debug) {
/*  483 */         ORBUtility.dprint(this, "Exiting destroyIfNotInitDone on poa " + this);
/*      */       }
/*      */ 
/*      */       
/*  487 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] internalReferenceToId(Object paramObject) throws WrongAdapter {
/*  494 */     IOR iOR = ORBUtility.getIOR(paramObject);
/*  495 */     IORTemplateList iORTemplateList1 = iOR.getIORTemplates();
/*      */     
/*  497 */     ObjectReferenceFactory objectReferenceFactory = getCurrentFactory();
/*      */     
/*  499 */     IORTemplateList iORTemplateList2 = IORFactories.getIORTemplateList(objectReferenceFactory);
/*      */     
/*  501 */     if (!iORTemplateList2.isEquivalent((IORFactory)iORTemplateList1)) {
/*  502 */       throw new WrongAdapter();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  508 */     Iterator<TaggedProfile> iterator = iOR.iterator();
/*  509 */     if (!iterator.hasNext())
/*  510 */       throw iorWrapper().noProfilesInIor(); 
/*  511 */     TaggedProfile taggedProfile = iterator.next();
/*  512 */     ObjectId objectId = taggedProfile.getObjectId();
/*      */     
/*  514 */     return objectId.getId();
/*      */   }
/*      */ 
/*      */   
/*      */   static class DestroyThread
/*      */     extends Thread
/*      */   {
/*      */     private boolean wait;
/*      */     private boolean etherealize;
/*      */     private boolean debug;
/*      */     private POAImpl thePoa;
/*      */     
/*      */     public DestroyThread(boolean param1Boolean1, boolean param1Boolean2) {
/*  527 */       this.etherealize = param1Boolean1;
/*  528 */       this.debug = param1Boolean2;
/*      */     }
/*      */ 
/*      */     
/*      */     public void doIt(POAImpl param1POAImpl, boolean param1Boolean) {
/*  533 */       if (this.debug) {
/*  534 */         ORBUtility.dprint(this, "Calling DestroyThread.doIt(thePOA=" + param1POAImpl + " wait=" + param1Boolean + " etherealize=" + this.etherealize);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  539 */       this.thePoa = param1POAImpl;
/*  540 */       this.wait = param1Boolean;
/*      */       
/*  542 */       if (param1Boolean) {
/*  543 */         run();
/*      */       } else {
/*      */ 
/*      */ 
/*      */         
/*  548 */         try { setDaemon(true); } catch (Exception exception) {}
/*  549 */         start();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*  555 */       HashSet hashSet = new HashSet();
/*      */       
/*  557 */       performDestroy(this.thePoa, hashSet);
/*      */       
/*  559 */       Iterator<ObjectReferenceTemplate> iterator = hashSet.iterator();
/*      */       
/*  561 */       ObjectReferenceTemplate[] arrayOfObjectReferenceTemplate = new ObjectReferenceTemplate[hashSet.size()];
/*  562 */       byte b = 0;
/*  563 */       while (iterator.hasNext()) {
/*  564 */         arrayOfObjectReferenceTemplate[b++] = iterator.next();
/*      */       }
/*  566 */       this.thePoa.getORB().getPIHandler().adapterStateChanged(arrayOfObjectReferenceTemplate, (short)4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean prepareForDestruction(POAImpl param1POAImpl, Set param1Set) {
/*  576 */       POAImpl[] arrayOfPOAImpl = null;
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  581 */         param1POAImpl.lock();
/*      */         
/*  583 */         if (this.debug) {
/*  584 */           ORBUtility.dprint(this, "Calling performDestroy on poa " + param1POAImpl);
/*      */         }
/*      */ 
/*      */         
/*  588 */         if (param1POAImpl.state <= 3) {
/*  589 */           param1POAImpl.state = 4;
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  599 */           if (this.wait) {
/*  600 */             while (param1POAImpl.state != 5) {
/*      */               try {
/*  602 */                 param1POAImpl.beingDestroyedCV.await();
/*  603 */               } catch (InterruptedException interruptedException) {}
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  608 */           return false;
/*      */         } 
/*      */         
/*  611 */         param1POAImpl.isDestroying.set(Boolean.TRUE);
/*      */ 
/*      */ 
/*      */         
/*  615 */         arrayOfPOAImpl = (POAImpl[])param1POAImpl.children.values().toArray((Object[])new POAImpl[0]);
/*      */       } finally {
/*      */         
/*  618 */         param1POAImpl.unlock();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  625 */       for (byte b = 0; b < arrayOfPOAImpl.length; b++) {
/*  626 */         performDestroy(arrayOfPOAImpl[b], param1Set);
/*      */       }
/*      */       
/*  629 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void performDestroy(POAImpl param1POAImpl, Set param1Set) {
/*  634 */       if (!prepareForDestruction(param1POAImpl, param1Set)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  643 */       POAImpl pOAImpl = param1POAImpl.parent;
/*  644 */       boolean bool = (pOAImpl == null) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  650 */         if (!bool) {
/*  651 */           pOAImpl.lock();
/*      */         }
/*      */         try {
/*  654 */           param1POAImpl.lock();
/*      */           
/*  656 */           completeDestruction(param1POAImpl, pOAImpl, param1Set);
/*      */         } finally {
/*      */           
/*  659 */           param1POAImpl.unlock();
/*      */           
/*  661 */           if (bool)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  666 */             param1POAImpl.manager.getFactory().registerRootPOA(); } 
/*      */         } 
/*      */       } finally {
/*  669 */         if (!bool) {
/*  670 */           pOAImpl.unlock();
/*  671 */           param1POAImpl.parent = null;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void completeDestruction(POAImpl param1POAImpl1, POAImpl param1POAImpl2, Set<ObjectReferenceTemplate> param1Set) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield debug : Z
/*      */       //   4: ifeq -> 30
/*      */       //   7: aload_0
/*      */       //   8: new java/lang/StringBuilder
/*      */       //   11: dup
/*      */       //   12: invokespecial <init> : ()V
/*      */       //   15: ldc 'Calling completeDestruction on poa '
/*      */       //   17: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   20: aload_1
/*      */       //   21: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */       //   24: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   27: invokestatic dprint : (Ljava/lang/Object;Ljava/lang/String;)V
/*      */       //   30: aload_1
/*      */       //   31: invokestatic access$500 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)I
/*      */       //   34: ifeq -> 52
/*      */       //   37: aload_1
/*      */       //   38: invokestatic access$600 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/orbutil/concurrent/CondVar;
/*      */       //   41: invokevirtual await : ()V
/*      */       //   44: goto -> 30
/*      */       //   47: astore #4
/*      */       //   49: goto -> 30
/*      */       //   52: aload_1
/*      */       //   53: invokestatic access$700 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/oa/poa/POAPolicyMediator;
/*      */       //   56: ifnull -> 84
/*      */       //   59: aload_0
/*      */       //   60: getfield etherealize : Z
/*      */       //   63: ifeq -> 75
/*      */       //   66: aload_1
/*      */       //   67: invokestatic access$700 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/oa/poa/POAPolicyMediator;
/*      */       //   70: invokeinterface etherealizeAll : ()V
/*      */       //   75: aload_1
/*      */       //   76: invokestatic access$700 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/oa/poa/POAPolicyMediator;
/*      */       //   79: invokeinterface clearAOM : ()V
/*      */       //   84: aload_1
/*      */       //   85: invokestatic access$400 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/oa/poa/POAManagerImpl;
/*      */       //   88: ifnull -> 99
/*      */       //   91: aload_1
/*      */       //   92: invokestatic access$400 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/oa/poa/POAManagerImpl;
/*      */       //   95: aload_1
/*      */       //   96: invokevirtual removePOA : (Lorg/omg/PortableServer/POA;)V
/*      */       //   99: aload_2
/*      */       //   100: ifnull -> 117
/*      */       //   103: aload_2
/*      */       //   104: invokestatic access$200 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Ljava/util/Map;
/*      */       //   107: aload_1
/*      */       //   108: invokestatic access$800 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Ljava/lang/String;
/*      */       //   111: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */       //   116: pop
/*      */       //   117: aload_3
/*      */       //   118: aload_1
/*      */       //   119: invokevirtual getAdapterTemplate : ()Lorg/omg/PortableInterceptor/ObjectReferenceTemplate;
/*      */       //   122: invokeinterface add : (Ljava/lang/Object;)Z
/*      */       //   127: pop
/*      */       //   128: aload_1
/*      */       //   129: iconst_5
/*      */       //   130: invokestatic access$002 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;I)I
/*      */       //   133: pop
/*      */       //   134: aload_1
/*      */       //   135: invokestatic access$100 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/orbutil/concurrent/CondVar;
/*      */       //   138: invokevirtual broadcast : ()V
/*      */       //   141: aload_1
/*      */       //   142: getfield isDestroying : Ljava/lang/ThreadLocal;
/*      */       //   145: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
/*      */       //   148: invokevirtual set : (Ljava/lang/Object;)V
/*      */       //   151: aload_0
/*      */       //   152: getfield debug : Z
/*      */       //   155: ifeq -> 328
/*      */       //   158: aload_0
/*      */       //   159: new java/lang/StringBuilder
/*      */       //   162: dup
/*      */       //   163: invokespecial <init> : ()V
/*      */       //   166: ldc 'Exiting completeDestruction on poa '
/*      */       //   168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   171: aload_1
/*      */       //   172: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */       //   175: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   178: invokestatic dprint : (Ljava/lang/Object;Ljava/lang/String;)V
/*      */       //   181: goto -> 328
/*      */       //   184: astore #4
/*      */       //   186: aload #4
/*      */       //   188: instanceof java/lang/ThreadDeath
/*      */       //   191: ifeq -> 200
/*      */       //   194: aload #4
/*      */       //   196: checkcast java/lang/ThreadDeath
/*      */       //   199: athrow
/*      */       //   200: aload_1
/*      */       //   201: invokevirtual lifecycleWrapper : ()Lcom/sun/corba/se/impl/logging/POASystemException;
/*      */       //   204: aload #4
/*      */       //   206: aload_1
/*      */       //   207: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   210: invokevirtual unexpectedException : (Ljava/lang/Throwable;Ljava/lang/Object;)Lorg/omg/CORBA/INTERNAL;
/*      */       //   213: pop
/*      */       //   214: aload_1
/*      */       //   215: iconst_5
/*      */       //   216: invokestatic access$002 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;I)I
/*      */       //   219: pop
/*      */       //   220: aload_1
/*      */       //   221: invokestatic access$100 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/orbutil/concurrent/CondVar;
/*      */       //   224: invokevirtual broadcast : ()V
/*      */       //   227: aload_1
/*      */       //   228: getfield isDestroying : Ljava/lang/ThreadLocal;
/*      */       //   231: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
/*      */       //   234: invokevirtual set : (Ljava/lang/Object;)V
/*      */       //   237: aload_0
/*      */       //   238: getfield debug : Z
/*      */       //   241: ifeq -> 328
/*      */       //   244: aload_0
/*      */       //   245: new java/lang/StringBuilder
/*      */       //   248: dup
/*      */       //   249: invokespecial <init> : ()V
/*      */       //   252: ldc 'Exiting completeDestruction on poa '
/*      */       //   254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   257: aload_1
/*      */       //   258: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */       //   261: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   264: invokestatic dprint : (Ljava/lang/Object;Ljava/lang/String;)V
/*      */       //   267: goto -> 328
/*      */       //   270: astore #5
/*      */       //   272: aload_1
/*      */       //   273: iconst_5
/*      */       //   274: invokestatic access$002 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;I)I
/*      */       //   277: pop
/*      */       //   278: aload_1
/*      */       //   279: invokestatic access$100 : (Lcom/sun/corba/se/impl/oa/poa/POAImpl;)Lcom/sun/corba/se/impl/orbutil/concurrent/CondVar;
/*      */       //   282: invokevirtual broadcast : ()V
/*      */       //   285: aload_1
/*      */       //   286: getfield isDestroying : Ljava/lang/ThreadLocal;
/*      */       //   289: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
/*      */       //   292: invokevirtual set : (Ljava/lang/Object;)V
/*      */       //   295: aload_0
/*      */       //   296: getfield debug : Z
/*      */       //   299: ifeq -> 325
/*      */       //   302: aload_0
/*      */       //   303: new java/lang/StringBuilder
/*      */       //   306: dup
/*      */       //   307: invokespecial <init> : ()V
/*      */       //   310: ldc 'Exiting completeDestruction on poa '
/*      */       //   312: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   315: aload_1
/*      */       //   316: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */       //   319: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   322: invokestatic dprint : (Ljava/lang/Object;Ljava/lang/String;)V
/*      */       //   325: aload #5
/*      */       //   327: athrow
/*      */       //   328: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #679	-> 0
/*      */       //   #680	-> 7
/*      */       //   #685	-> 30
/*      */       //   #687	-> 37
/*      */       //   #690	-> 44
/*      */       //   #688	-> 47
/*      */       //   #690	-> 49
/*      */       //   #693	-> 52
/*      */       //   #694	-> 59
/*      */       //   #695	-> 66
/*      */       //   #697	-> 75
/*      */       //   #700	-> 84
/*      */       //   #701	-> 91
/*      */       //   #703	-> 99
/*      */       //   #704	-> 103
/*      */       //   #706	-> 117
/*      */       //   #713	-> 128
/*      */       //   #714	-> 134
/*      */       //   #715	-> 141
/*      */       //   #717	-> 151
/*      */       //   #718	-> 158
/*      */       //   #707	-> 184
/*      */       //   #708	-> 186
/*      */       //   #709	-> 194
/*      */       //   #711	-> 200
/*      */       //   #713	-> 214
/*      */       //   #714	-> 220
/*      */       //   #715	-> 227
/*      */       //   #717	-> 237
/*      */       //   #718	-> 244
/*      */       //   #713	-> 270
/*      */       //   #714	-> 278
/*      */       //   #715	-> 285
/*      */       //   #717	-> 295
/*      */       //   #718	-> 302
/*      */       //   #721	-> 325
/*      */       //   #722	-> 328
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   30	128	184	java/lang/Throwable
/*      */       //   30	128	270	finally
/*      */       //   37	44	47	java/lang/InterruptedException
/*      */       //   184	214	270	finally
/*      */       //   270	272	270	finally
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void etherealizeAll() {
/*      */     try {
/*  728 */       lock();
/*      */       
/*  730 */       if (this.debug) {
/*  731 */         ORBUtility.dprint(this, "Calling etheralizeAll on poa " + this);
/*      */       }
/*      */ 
/*      */       
/*  735 */       this.mediator.etherealizeAll();
/*      */     } finally {
/*  737 */       if (this.debug) {
/*  738 */         ORBUtility.dprint(this, "Exiting etheralizeAll on poa " + this);
/*      */       }
/*      */ 
/*      */       
/*  742 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POA create_POA(String paramString, POAManager paramPOAManager, Policy[] paramArrayOfPolicy) throws AdapterAlreadyExists, InvalidPolicy {
/*      */     try {
/*  759 */       lock();
/*      */       
/*  761 */       if (this.debug) {
/*  762 */         ORBUtility.dprint(this, "Calling create_POA(name=" + paramString + " theManager=" + paramPOAManager + " policies=" + paramArrayOfPolicy + ") on poa " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  769 */       if (this.state > 3) {
/*  770 */         throw omgLifecycleWrapper().createPoaDestroy();
/*      */       }
/*  772 */       POAImpl pOAImpl = (POAImpl)this.children.get(paramString);
/*      */       
/*  774 */       if (pOAImpl == null) {
/*  775 */         pOAImpl = new POAImpl(paramString, this, getORB(), 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  806 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POA find_POA(String paramString, boolean paramBoolean) throws AdapterNonExistent {
/*  817 */     POAImpl pOAImpl = null;
/*  818 */     AdapterActivator adapterActivator = null;
/*      */     
/*  820 */     lock();
/*      */     
/*  822 */     if (this.debug) {
/*  823 */       ORBUtility.dprint(this, "Calling find_POA(name=" + paramString + " activate=" + paramBoolean + ") on poa " + this);
/*      */     }
/*      */ 
/*      */     
/*  827 */     pOAImpl = (POAImpl)this.children.get(paramString);
/*      */     
/*  829 */     if (pOAImpl != null) {
/*  830 */       if (this.debug) {
/*  831 */         ORBUtility.dprint(this, "Calling find_POA: found poa " + pOAImpl);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  836 */         pOAImpl.lock();
/*      */ 
/*      */ 
/*      */         
/*  840 */         unlock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  846 */         if (!pOAImpl.waitUntilRunning()) {
/*  847 */           throw omgLifecycleWrapper().poaDestroyed();
/*      */         
/*      */         }
/*      */       }
/*      */       finally {
/*      */         
/*  853 */         pOAImpl.unlock();
/*      */       } 
/*      */     } else {
/*      */       try {
/*  857 */         if (this.debug) {
/*  858 */           ORBUtility.dprint(this, "Calling find_POA: no poa found");
/*      */         }
/*      */ 
/*      */         
/*  862 */         if (paramBoolean && this.activator != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  876 */           pOAImpl = new POAImpl(paramString, this, getORB(), 1);
/*      */           
/*  878 */           if (this.debug) {
/*  879 */             ORBUtility.dprint(this, "Calling find_POA: created poa " + pOAImpl);
/*      */           }
/*      */ 
/*      */           
/*  883 */           adapterActivator = this.activator;
/*      */         } else {
/*  885 */           throw new AdapterNonExistent();
/*      */         } 
/*      */       } finally {
/*  888 */         unlock();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     if (adapterActivator != null) {
/*  900 */       boolean bool1 = false;
/*  901 */       boolean bool2 = false;
/*      */       
/*  903 */       if (this.debug) {
/*  904 */         ORBUtility.dprint(this, "Calling find_POA: calling AdapterActivator");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  911 */         synchronized (adapterActivator) {
/*  912 */           bool1 = adapterActivator.unknown_adapter(this, paramString);
/*      */         } 
/*  914 */       } catch (SystemException systemException) {
/*  915 */         throw omgLifecycleWrapper().adapterActivatorException(systemException, paramString, this.poaId
/*  916 */             .toString());
/*  917 */       } catch (Throwable throwable) {
/*      */ 
/*      */         
/*  920 */         lifecycleWrapper().unexpectedException(throwable, toString());
/*      */         
/*  922 */         if (throwable instanceof ThreadDeath) {
/*  923 */           throw (ThreadDeath)throwable;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/*  930 */         bool2 = pOAImpl.destroyIfNotInitDone();
/*      */       } 
/*      */       
/*  933 */       if (bool1) {
/*  934 */         if (!bool2)
/*  935 */           throw omgLifecycleWrapper().adapterActivatorException(paramString, this.poaId
/*  936 */               .toString()); 
/*      */       } else {
/*  938 */         if (this.debug) {
/*  939 */           ORBUtility.dprint(this, "Calling find_POA: AdapterActivator returned false");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  945 */         throw new AdapterNonExistent();
/*      */       } 
/*      */     } 
/*      */     
/*  949 */     return pOAImpl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy(boolean paramBoolean1, boolean paramBoolean2) {
/*  959 */     if (paramBoolean2 && getORB().isDuringDispatch()) {
/*  960 */       throw lifecycleWrapper().destroyDeadlock();
/*      */     }
/*      */     
/*  963 */     DestroyThread destroyThread = new DestroyThread(paramBoolean1, this.debug);
/*  964 */     destroyThread.doIt(this, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThreadPolicy create_thread_policy(ThreadPolicyValue paramThreadPolicyValue) {
/*  974 */     return new ThreadPolicyImpl(paramThreadPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LifespanPolicy create_lifespan_policy(LifespanPolicyValue paramLifespanPolicyValue) {
/*  984 */     return new LifespanPolicyImpl(paramLifespanPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdUniquenessPolicy create_id_uniqueness_policy(IdUniquenessPolicyValue paramIdUniquenessPolicyValue) {
/*  994 */     return new IdUniquenessPolicyImpl(paramIdUniquenessPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdAssignmentPolicy create_id_assignment_policy(IdAssignmentPolicyValue paramIdAssignmentPolicyValue) {
/* 1004 */     return new IdAssignmentPolicyImpl(paramIdAssignmentPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImplicitActivationPolicy create_implicit_activation_policy(ImplicitActivationPolicyValue paramImplicitActivationPolicyValue) {
/* 1014 */     return new ImplicitActivationPolicyImpl(paramImplicitActivationPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServantRetentionPolicy create_servant_retention_policy(ServantRetentionPolicyValue paramServantRetentionPolicyValue) {
/* 1024 */     return new ServantRetentionPolicyImpl(paramServantRetentionPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RequestProcessingPolicy create_request_processing_policy(RequestProcessingPolicyValue paramRequestProcessingPolicyValue) {
/* 1034 */     return new RequestProcessingPolicyImpl(paramRequestProcessingPolicyValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String the_name() {
/*      */     try {
/* 1044 */       lock();
/*      */       
/* 1046 */       return this.name;
/*      */     } finally {
/* 1048 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POA the_parent() {
/*      */     try {
/* 1059 */       lock();
/*      */       
/* 1061 */       return this.parent;
/*      */     } finally {
/* 1063 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POA[] the_children() {
/*      */     try {
/* 1073 */       lock();
/*      */       
/* 1075 */       Collection collection = this.children.values();
/* 1076 */       int i = collection.size();
/* 1077 */       POA[] arrayOfPOA = new POA[i];
/* 1078 */       byte b = 0;
/* 1079 */       Iterator<POA> iterator = collection.iterator();
/* 1080 */       while (iterator.hasNext()) {
/* 1081 */         POA pOA = iterator.next();
/* 1082 */         arrayOfPOA[b++] = pOA;
/*      */       } 
/*      */       
/* 1085 */       return arrayOfPOA;
/*      */     } finally {
/* 1087 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public POAManager the_POAManager() {
/*      */     try {
/* 1098 */       lock();
/*      */       
/* 1100 */       return this.manager;
/*      */     } finally {
/* 1102 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AdapterActivator the_activator() {
/*      */     try {
/* 1113 */       lock();
/*      */       
/* 1115 */       return this.activator;
/*      */     } finally {
/* 1117 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void the_activator(AdapterActivator paramAdapterActivator) {
/*      */     try {
/* 1128 */       lock();
/*      */       
/* 1130 */       if (this.debug) {
/* 1131 */         ORBUtility.dprint(this, "Calling the_activator on poa " + this + " activator=" + paramAdapterActivator);
/*      */       }
/*      */ 
/*      */       
/* 1135 */       this.activator = paramAdapterActivator;
/*      */     } finally {
/* 1137 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ServantManager get_servant_manager() throws WrongPolicy {
/*      */     try {
/* 1148 */       lock();
/*      */       
/* 1150 */       return this.mediator.getServantManager();
/*      */     } finally {
/* 1152 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_servant_manager(ServantManager paramServantManager) throws WrongPolicy {
/*      */     try {
/* 1164 */       lock();
/*      */       
/* 1166 */       if (this.debug) {
/* 1167 */         ORBUtility.dprint(this, "Calling set_servant_manager on poa " + this + " servantManager=" + paramServantManager);
/*      */       }
/*      */ 
/*      */       
/* 1171 */       this.mediator.setServantManager(paramServantManager);
/*      */     } finally {
/* 1173 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Servant get_servant() throws NoServant, WrongPolicy {
/*      */     try {
/* 1184 */       lock();
/*      */       
/* 1186 */       return this.mediator.getDefaultServant();
/*      */     } finally {
/* 1188 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_servant(Servant paramServant) throws WrongPolicy {
/*      */     try {
/* 1200 */       lock();
/*      */       
/* 1202 */       if (this.debug) {
/* 1203 */         ORBUtility.dprint(this, "Calling set_servant on poa " + this + " defaultServant=" + paramServant);
/*      */       }
/*      */ 
/*      */       
/* 1207 */       this.mediator.setDefaultServant(paramServant);
/*      */     } finally {
/* 1209 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] activate_object(Servant paramServant) throws ServantAlreadyActive, WrongPolicy {
/*      */     try {
/* 1221 */       lock();
/*      */       
/* 1223 */       if (this.debug) {
/* 1224 */         ORBUtility.dprint(this, "Calling activate_object on poa " + this + " (servant=" + paramServant + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1232 */       byte[] arrayOfByte = this.mediator.newSystemId();
/*      */       
/*      */       try {
/* 1235 */         this.mediator.activateObject(arrayOfByte, paramServant);
/* 1236 */       } catch (ObjectAlreadyActive objectAlreadyActive) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1242 */       return arrayOfByte;
/*      */     } finally {
/* 1244 */       if (this.debug) {
/* 1245 */         ORBUtility.dprint(this, "Exiting activate_object on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1249 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void activate_object_with_id(byte[] paramArrayOfbyte, Servant paramServant) throws ObjectAlreadyActive, ServantAlreadyActive, WrongPolicy {
/*      */     try {
/* 1262 */       lock();
/*      */       
/* 1264 */       if (this.debug) {
/* 1265 */         ORBUtility.dprint(this, "Calling activate_object_with_id on poa " + this + " (servant=" + paramServant + " id=" + paramArrayOfbyte + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1272 */       byte[] arrayOfByte = (byte[])paramArrayOfbyte.clone();
/*      */       
/* 1274 */       this.mediator.activateObject(arrayOfByte, paramServant);
/*      */     } finally {
/* 1276 */       if (this.debug) {
/* 1277 */         ORBUtility.dprint(this, "Exiting activate_object_with_id on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1281 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deactivate_object(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/*      */     try {
/* 1293 */       lock();
/*      */       
/* 1295 */       if (this.debug) {
/* 1296 */         ORBUtility.dprint(this, "Calling deactivate_object on poa " + this + " (id=" + paramArrayOfbyte + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1301 */       this.mediator.deactivateObject(paramArrayOfbyte);
/*      */     } finally {
/* 1303 */       if (this.debug) {
/* 1304 */         ORBUtility.dprint(this, "Exiting deactivate_object on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1308 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object create_reference(String paramString) throws WrongPolicy {
/*      */     try {
/* 1320 */       lock();
/*      */       
/* 1322 */       if (this.debug) {
/* 1323 */         ORBUtility.dprint(this, "Calling create_reference(repId=" + paramString + ") on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1327 */       return makeObject(paramString, this.mediator.newSystemId());
/*      */     } finally {
/* 1329 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object create_reference_with_id(byte[] paramArrayOfbyte, String paramString) {
/*      */     try {
/* 1341 */       lock();
/*      */       
/* 1343 */       if (this.debug) {
/* 1344 */         ORBUtility.dprint(this, "Calling create_reference_with_id(oid=" + paramArrayOfbyte + " repId=" + paramString + ") on poa " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1351 */       byte[] arrayOfByte = (byte[])paramArrayOfbyte.clone();
/*      */       
/* 1353 */       return makeObject(paramString, arrayOfByte);
/*      */     } finally {
/* 1355 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] servant_to_id(Servant paramServant) throws ServantNotActive, WrongPolicy {
/*      */     try {
/* 1367 */       lock();
/*      */       
/* 1369 */       if (this.debug) {
/* 1370 */         ORBUtility.dprint(this, "Calling servant_to_id(servant=" + paramServant + ") on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1374 */       return this.mediator.servantToId(paramServant);
/*      */     } finally {
/* 1376 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object servant_to_reference(Servant paramServant) throws ServantNotActive, WrongPolicy {
/*      */     try {
/* 1388 */       lock();
/*      */       
/* 1390 */       if (this.debug) {
/* 1391 */         ORBUtility.dprint(this, "Calling servant_to_reference(servant=" + paramServant + ") on poa " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1396 */       byte[] arrayOfByte = this.mediator.servantToId(paramServant);
/* 1397 */       String str = paramServant._all_interfaces(this, arrayOfByte)[0];
/* 1398 */       return create_reference_with_id(arrayOfByte, str);
/*      */     } finally {
/* 1400 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Servant reference_to_servant(Object paramObject) throws ObjectNotActive, WrongPolicy, WrongAdapter {
/*      */     try {
/* 1412 */       lock();
/*      */       
/* 1414 */       if (this.debug) {
/* 1415 */         ORBUtility.dprint(this, "Calling reference_to_servant(reference=" + paramObject + ") on poa " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1420 */       if (this.state >= 4) {
/* 1421 */         throw lifecycleWrapper().adapterDestroyed();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1426 */       byte[] arrayOfByte = internalReferenceToId(paramObject);
/*      */       
/* 1428 */       return this.mediator.idToServant(arrayOfByte);
/*      */     } finally {
/* 1430 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] reference_to_id(Object paramObject) throws WrongAdapter, WrongPolicy {
/*      */     try {
/* 1442 */       lock();
/*      */       
/* 1444 */       if (this.debug) {
/* 1445 */         ORBUtility.dprint(this, "Calling reference_to_id(reference=" + paramObject + ") on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1449 */       if (this.state >= 4) {
/* 1450 */         throw lifecycleWrapper().adapterDestroyed();
/*      */       }
/*      */       
/* 1453 */       return internalReferenceToId(paramObject);
/*      */     } finally {
/* 1455 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Servant id_to_servant(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/*      */     try {
/* 1467 */       lock();
/*      */       
/* 1469 */       if (this.debug) {
/* 1470 */         ORBUtility.dprint(this, "Calling id_to_servant(id=" + paramArrayOfbyte + ") on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1474 */       if (this.state >= 4) {
/* 1475 */         throw lifecycleWrapper().adapterDestroyed();
/*      */       }
/* 1477 */       return this.mediator.idToServant(paramArrayOfbyte);
/*      */     } finally {
/* 1479 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object id_to_reference(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/*      */     try {
/* 1492 */       lock();
/*      */       
/* 1494 */       if (this.debug) {
/* 1495 */         ORBUtility.dprint(this, "Calling id_to_reference(id=" + paramArrayOfbyte + ") on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1499 */       if (this.state >= 4) {
/* 1500 */         throw lifecycleWrapper().adapterDestroyed();
/*      */       }
/*      */       
/* 1503 */       Servant servant = this.mediator.idToServant(paramArrayOfbyte);
/* 1504 */       String str = servant._all_interfaces(this, paramArrayOfbyte)[0];
/* 1505 */       return makeObject(str, paramArrayOfbyte);
/*      */     } finally {
/* 1507 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] id() {
/*      */     try {
/* 1518 */       lock();
/*      */       
/* 1520 */       return getAdapterId();
/*      */     } finally {
/* 1522 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Policy getEffectivePolicy(int paramInt) {
/* 1532 */     return this.mediator.getPolicies().get_effective_policy(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getManagerId() {
/* 1537 */     return this.manager.getManagerId();
/*      */   }
/*      */ 
/*      */   
/*      */   public short getState() {
/* 1542 */     return this.manager.getORTState();
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getInterfaces(Object paramObject, byte[] paramArrayOfbyte) {
/* 1547 */     Servant servant = (Servant)paramObject;
/* 1548 */     return servant._all_interfaces(this, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ObjectCopierFactory getObjectCopierFactory() {
/* 1553 */     int i = this.mediator.getPolicies().getCopierId();
/* 1554 */     CopierManager copierManager = getORB().getCopierManager();
/* 1555 */     return copierManager.getObjectCopierFactory(i);
/*      */   }
/*      */ 
/*      */   
/*      */   public void enter() throws OADestroyed {
/*      */     try {
/* 1561 */       lock();
/*      */       
/* 1563 */       if (this.debug) {
/* 1564 */         ORBUtility.dprint(this, "Calling enter on poa " + this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1572 */       while (this.state == 4 && this.isDestroying
/* 1573 */         .get() == Boolean.FALSE) {
/*      */         try {
/* 1575 */           this.beingDestroyedCV.await();
/* 1576 */         } catch (InterruptedException interruptedException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1581 */       if (!waitUntilRunning()) {
/* 1582 */         throw new OADestroyed();
/*      */       }
/* 1584 */       this.invocationCount++;
/*      */     } finally {
/* 1586 */       if (this.debug) {
/* 1587 */         ORBUtility.dprint(this, "Exiting enter on poa " + this);
/*      */       }
/*      */       
/* 1590 */       unlock();
/*      */     } 
/*      */     
/* 1593 */     this.manager.enter();
/*      */   }
/*      */ 
/*      */   
/*      */   public void exit() {
/*      */     try {
/* 1599 */       lock();
/*      */       
/* 1601 */       if (this.debug) {
/* 1602 */         ORBUtility.dprint(this, "Calling exit on poa " + this);
/*      */       }
/*      */       
/* 1605 */       this.invocationCount--;
/*      */       
/* 1607 */       if (this.invocationCount == 0 && this.state == 4) {
/* 1608 */         this.invokeCV.broadcast();
/*      */       }
/*      */     } finally {
/* 1611 */       if (this.debug) {
/* 1612 */         ORBUtility.dprint(this, "Exiting exit on poa " + this);
/*      */       }
/*      */       
/* 1615 */       unlock();
/*      */     } 
/*      */     
/* 1618 */     this.manager.exit();
/*      */   }
/*      */ 
/*      */   
/*      */   public void getInvocationServant(OAInvocationInfo paramOAInvocationInfo) {
/*      */     try {
/* 1624 */       lock();
/*      */       
/* 1626 */       if (this.debug) {
/* 1627 */         ORBUtility.dprint(this, "Calling getInvocationServant on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1631 */       Object object = null;
/*      */       
/*      */       try {
/* 1634 */         object = this.mediator.getInvocationServant(paramOAInvocationInfo.id(), paramOAInvocationInfo
/* 1635 */             .getOperation());
/* 1636 */       } catch (ForwardRequest forwardRequest) {
/* 1637 */         throw new ForwardException(getORB(), forwardRequest.forward_reference);
/*      */       } 
/*      */       
/* 1640 */       paramOAInvocationInfo.setServant(object);
/*      */     } finally {
/* 1642 */       if (this.debug) {
/* 1643 */         ORBUtility.dprint(this, "Exiting getInvocationServant on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1647 */       unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getLocalServant(byte[] paramArrayOfbyte) {
/* 1653 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void returnServant() {
/*      */     try {
/* 1664 */       lock();
/*      */       
/* 1666 */       if (this.debug) {
/* 1667 */         ORBUtility.dprint(this, "Calling returnServant on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1671 */       this.mediator.returnServant();
/* 1672 */     } catch (Throwable throwable) {
/* 1673 */       if (this.debug) {
/* 1674 */         ORBUtility.dprint(this, "Exception " + throwable + " in returnServant on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1678 */       if (throwable instanceof Error)
/* 1679 */         throw (Error)throwable; 
/* 1680 */       if (throwable instanceof RuntimeException) {
/* 1681 */         throw (RuntimeException)throwable;
/*      */       }
/*      */     } finally {
/* 1684 */       if (this.debug) {
/* 1685 */         ORBUtility.dprint(this, "Exiting returnServant on poa " + this);
/*      */       }
/*      */ 
/*      */       
/* 1689 */       unlock();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */