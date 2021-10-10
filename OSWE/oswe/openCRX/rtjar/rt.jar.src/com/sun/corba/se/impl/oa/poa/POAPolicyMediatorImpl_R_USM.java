/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.oa.NullServantImpl;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.PortableServer.ForwardRequest;
/*     */ import org.omg.PortableServer.POAPackage.NoServant;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.ServantActivator;
/*     */ import org.omg.PortableServer.ServantManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class POAPolicyMediatorImpl_R_USM
/*     */   extends POAPolicyMediatorBase_R
/*     */ {
/*     */   protected ServantActivator activator;
/*     */   
/*     */   POAPolicyMediatorImpl_R_USM(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  63 */     super(paramPolicies, paramPOAImpl);
/*  64 */     this.activator = null;
/*     */     
/*  66 */     if (!paramPolicies.useServantManager()) {
/*  67 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
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
/*     */   private AOMEntry enterEntry(ActiveObjectMap.Key paramKey) {
/*     */     boolean bool;
/*  81 */     AOMEntry aOMEntry = null;
/*     */     
/*     */     do {
/*  84 */       bool = false;
/*  85 */       aOMEntry = this.activeObjectMap.get(paramKey);
/*     */       
/*     */       try {
/*  88 */         aOMEntry.enter();
/*  89 */       } catch (Exception exception) {
/*  90 */         bool = true;
/*     */       } 
/*  92 */     } while (bool);
/*     */     
/*  94 */     return aOMEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalGetServant(byte[] paramArrayOfbyte, String paramString) throws ForwardRequest {
/* 100 */     if (this.poa.getDebug()) {
/* 101 */       ORBUtility.dprint(this, "Calling POAPolicyMediatorImpl_R_USM.internalGetServant for poa " + this.poa + " operation=" + paramString);
/*     */     }
/*     */     
/*     */     try {
/*     */       NullServantImpl nullServantImpl;
/*     */       
/* 107 */       ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/* 108 */       AOMEntry aOMEntry = enterEntry(key);
/* 109 */       Servant servant = this.activeObjectMap.getServant(aOMEntry);
/* 110 */       if (servant != null) {
/* 111 */         if (this.poa.getDebug()) {
/* 112 */           ORBUtility.dprint(this, "internalGetServant: servant already activated");
/*     */         }
/*     */ 
/*     */         
/* 116 */         return servant;
/*     */       } 
/*     */       
/* 119 */       if (this.activator == null) {
/* 120 */         if (this.poa.getDebug()) {
/* 121 */           ORBUtility.dprint(this, "internalGetServant: no servant activator in POA");
/*     */         }
/*     */ 
/*     */         
/* 125 */         aOMEntry.incarnateFailure();
/* 126 */         throw this.poa.invocationWrapper().poaNoServantManager();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 134 */         if (this.poa.getDebug()) {
/* 135 */           ORBUtility.dprint(this, "internalGetServant: upcall to incarnate");
/*     */         }
/*     */ 
/*     */         
/* 139 */         this.poa.unlock();
/*     */         
/* 141 */         servant = this.activator.incarnate(paramArrayOfbyte, this.poa);
/*     */         
/* 143 */         if (servant == null)
/*     */         {
/* 145 */           nullServantImpl = new NullServantImpl((SystemException)this.poa.omgInvocationWrapper().nullServantReturned()); } 
/* 146 */       } catch (ForwardRequest forwardRequest) {
/* 147 */         if (this.poa.getDebug()) {
/* 148 */           ORBUtility.dprint(this, "internalGetServant: incarnate threw ForwardRequest");
/*     */         }
/*     */ 
/*     */         
/* 152 */         throw forwardRequest;
/* 153 */       } catch (SystemException systemException) {
/* 154 */         if (this.poa.getDebug()) {
/* 155 */           ORBUtility.dprint(this, "internalGetServant: incarnate threw SystemException " + systemException);
/*     */         }
/*     */ 
/*     */         
/* 159 */         throw systemException;
/* 160 */       } catch (Throwable throwable) {
/* 161 */         if (this.poa.getDebug()) {
/* 162 */           ORBUtility.dprint(this, "internalGetServant: incarnate threw Throwable " + throwable);
/*     */         }
/*     */ 
/*     */         
/* 166 */         throw this.poa.invocationWrapper().poaServantActivatorLookupFailed(throwable);
/*     */       } finally {
/*     */         
/* 169 */         this.poa.lock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 175 */         if (nullServantImpl == null || nullServantImpl instanceof com.sun.corba.se.spi.oa.NullServant) {
/* 176 */           if (this.poa.getDebug()) {
/* 177 */             ORBUtility.dprint(this, "internalGetServant: incarnate failed");
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 189 */           aOMEntry.incarnateFailure();
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 195 */           if (this.isUnique)
/*     */           {
/* 197 */             if (this.activeObjectMap.contains((Servant)nullServantImpl)) {
/* 198 */               if (this.poa.getDebug()) {
/* 199 */                 ORBUtility.dprint(this, "internalGetServant: servant already assigned to ID");
/*     */               }
/*     */ 
/*     */               
/* 203 */               aOMEntry.incarnateFailure();
/* 204 */               throw this.poa.invocationWrapper().poaServantNotUnique();
/*     */             } 
/*     */           }
/*     */           
/* 208 */           if (this.poa.getDebug()) {
/* 209 */             ORBUtility.dprint(this, "internalGetServant: incarnate complete");
/*     */           }
/*     */ 
/*     */           
/* 213 */           aOMEntry.incarnateComplete();
/* 214 */           activateServant(key, aOMEntry, (Servant)nullServantImpl);
/*     */         } 
/*     */       } 
/*     */       
/* 218 */       return nullServantImpl;
/*     */     } finally {
/* 220 */       if (this.poa.getDebug()) {
/* 221 */         ORBUtility.dprint(this, "Exiting POAPolicyMediatorImpl_R_USM.internalGetServant for poa " + this.poa);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnServant() {
/* 230 */     OAInvocationInfo oAInvocationInfo = this.orb.peekInvocationInfo();
/* 231 */     byte[] arrayOfByte = oAInvocationInfo.id();
/* 232 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(arrayOfByte);
/* 233 */     AOMEntry aOMEntry = this.activeObjectMap.get(key);
/* 234 */     aOMEntry.exit();
/*     */   }
/*     */ 
/*     */   
/*     */   public void etherealizeAll() {
/* 239 */     if (this.activator != null) {
/* 240 */       Set set = this.activeObjectMap.keySet();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       ActiveObjectMap.Key[] arrayOfKey = (ActiveObjectMap.Key[])set.toArray(
/* 246 */           (Object[])new ActiveObjectMap.Key[set.size()]);
/*     */       
/* 248 */       for (byte b = 0; b < set.size(); b++) {
/* 249 */         ActiveObjectMap.Key key = arrayOfKey[b];
/* 250 */         AOMEntry aOMEntry = this.activeObjectMap.get(key);
/* 251 */         Servant servant = this.activeObjectMap.getServant(aOMEntry);
/* 252 */         if (servant != null)
/*     */         {
/* 254 */           boolean bool = this.activeObjectMap.hasMultipleIDs(aOMEntry);
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public ServantManager getServantManager() throws WrongPolicy {
/* 281 */     return (ServantManager)this.activator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServantManager(ServantManager paramServantManager) throws WrongPolicy {
/* 287 */     if (this.activator != null) {
/* 288 */       throw this.poa.invocationWrapper().servantManagerAlreadySet();
/*     */     }
/* 290 */     if (paramServantManager instanceof ServantActivator) {
/* 291 */       this.activator = (ServantActivator)paramServantManager;
/*     */     } else {
/* 293 */       throw this.poa.invocationWrapper().servantManagerBadType();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Servant getDefaultServant() throws NoServant, WrongPolicy {
/* 298 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultServant(Servant paramServant) throws WrongPolicy {
/* 303 */     throw new WrongPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   class Etherealizer
/*     */     extends Thread
/*     */   {
/*     */     private POAPolicyMediatorImpl_R_USM mediator;
/*     */     private ActiveObjectMap.Key key;
/*     */     private AOMEntry entry;
/*     */     private Servant servant;
/*     */     private boolean debug;
/*     */     
/*     */     public Etherealizer(POAPolicyMediatorImpl_R_USM param1POAPolicyMediatorImpl_R_USM1, ActiveObjectMap.Key param1Key, AOMEntry param1AOMEntry, Servant param1Servant, boolean param1Boolean) {
/* 317 */       this.mediator = param1POAPolicyMediatorImpl_R_USM1;
/* 318 */       this.key = param1Key;
/* 319 */       this.entry = param1AOMEntry;
/* 320 */       this.servant = param1Servant;
/* 321 */       this.debug = param1Boolean;
/*     */     }
/*     */     
/*     */     public void run() {
/* 325 */       if (this.debug) {
/* 326 */         ORBUtility.dprint(this, "Calling Etherealizer.run on key " + this.key);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*     */         try {
/* 332 */           this.mediator.activator.etherealize(this.key.id, this.mediator.poa, this.servant, false, this.mediator.activeObjectMap
/* 333 */               .hasMultipleIDs(this.entry));
/* 334 */         } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 339 */           this.mediator.poa.lock();
/*     */           
/* 341 */           this.entry.etherealizeComplete();
/* 342 */           this.mediator.activeObjectMap.remove(this.key);
/*     */           
/* 344 */           POAManagerImpl pOAManagerImpl = (POAManagerImpl)this.mediator.poa.the_POAManager();
/* 345 */           POAFactory pOAFactory = pOAManagerImpl.getFactory();
/* 346 */           pOAFactory.unregisterPOAForServant(this.mediator.poa, this.servant);
/*     */         } finally {
/* 348 */           this.mediator.poa.unlock();
/*     */         } 
/*     */       } finally {
/* 351 */         if (this.debug) {
/* 352 */           ORBUtility.dprint(this, "Exiting Etherealizer.run");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deactivateHelper(ActiveObjectMap.Key paramKey, AOMEntry paramAOMEntry, Servant paramServant) throws ObjectNotActive, WrongPolicy {
/* 361 */     if (this.activator == null) {
/* 362 */       throw this.poa.invocationWrapper().poaNoServantManager();
/*     */     }
/* 364 */     Etherealizer etherealizer = new Etherealizer(this, paramKey, paramAOMEntry, paramServant, this.poa.getDebug());
/* 365 */     paramAOMEntry.startEtherealize(etherealizer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant idToServant(byte[] paramArrayOfbyte) throws WrongPolicy, ObjectNotActive {
/* 371 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/* 372 */     AOMEntry aOMEntry = this.activeObjectMap.get(key);
/*     */     
/* 374 */     Servant servant = this.activeObjectMap.getServant(aOMEntry);
/* 375 */     if (servant != null) {
/* 376 */       return servant;
/*     */     }
/* 378 */     throw new ObjectNotActive();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorImpl_R_USM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */