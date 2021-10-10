/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class POAPolicyMediatorBase_R
/*     */   extends POAPolicyMediatorBase
/*     */ {
/*     */   protected ActiveObjectMap activeObjectMap;
/*     */   
/*     */   POAPolicyMediatorBase_R(Policies paramPolicies, POAImpl paramPOAImpl) {
/*  50 */     super(paramPolicies, paramPOAImpl);
/*     */ 
/*     */     
/*  53 */     if (!paramPolicies.retainServants()) {
/*  54 */       throw paramPOAImpl.invocationWrapper().policyMediatorBadPolicyInFactory();
/*     */     }
/*  56 */     this.activeObjectMap = ActiveObjectMap.create(paramPOAImpl, !this.isUnique);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnServant() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAOM() {
/*  66 */     this.activeObjectMap.clear();
/*  67 */     this.activeObjectMap = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Servant internalKeyToServant(ActiveObjectMap.Key paramKey) {
/*  72 */     AOMEntry aOMEntry = this.activeObjectMap.get(paramKey);
/*  73 */     if (aOMEntry == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     return this.activeObjectMap.getServant(aOMEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Servant internalIdToServant(byte[] paramArrayOfbyte) {
/*  81 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/*  82 */     return internalKeyToServant(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void activateServant(ActiveObjectMap.Key paramKey, AOMEntry paramAOMEntry, Servant paramServant) {
/*  87 */     setDelegate(paramServant, paramKey.id);
/*     */     
/*  89 */     if (this.orb.shutdownDebugFlag) {
/*  90 */       System.out.println("Activating object " + paramServant + " with POA " + this.poa);
/*     */     }
/*     */ 
/*     */     
/*  94 */     this.activeObjectMap.putServant(paramServant, paramAOMEntry);
/*     */     
/*  96 */     if (Util.isInstanceDefined()) {
/*  97 */       POAManagerImpl pOAManagerImpl = (POAManagerImpl)this.poa.the_POAManager();
/*  98 */       POAFactory pOAFactory = pOAManagerImpl.getFactory();
/*  99 */       pOAFactory.registerPOAForServant(this.poa, paramServant);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void activateObject(byte[] paramArrayOfbyte, Servant paramServant) throws WrongPolicy, ServantAlreadyActive, ObjectAlreadyActive {
/* 106 */     if (this.isUnique && this.activeObjectMap.contains(paramServant))
/* 107 */       throw new ServantAlreadyActive(); 
/* 108 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/*     */     
/* 110 */     AOMEntry aOMEntry = this.activeObjectMap.get(key);
/*     */ 
/*     */     
/* 113 */     aOMEntry.activateObject();
/* 114 */     activateServant(key, aOMEntry, paramServant);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant deactivateObject(byte[] paramArrayOfbyte) throws ObjectNotActive, WrongPolicy {
/* 120 */     ActiveObjectMap.Key key = new ActiveObjectMap.Key(paramArrayOfbyte);
/* 121 */     return deactivateObject(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deactivateHelper(ActiveObjectMap.Key paramKey, AOMEntry paramAOMEntry, Servant paramServant) throws ObjectNotActive, WrongPolicy {
/* 130 */     this.activeObjectMap.remove(paramKey);
/*     */     
/* 132 */     if (Util.isInstanceDefined()) {
/* 133 */       POAManagerImpl pOAManagerImpl = (POAManagerImpl)this.poa.the_POAManager();
/* 134 */       POAFactory pOAFactory = pOAManagerImpl.getFactory();
/* 135 */       pOAFactory.unregisterPOAForServant(this.poa, paramServant);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant deactivateObject(ActiveObjectMap.Key paramKey) throws ObjectNotActive, WrongPolicy {
/* 142 */     if (this.orb.poaDebugFlag) {
/* 143 */       ORBUtility.dprint(this, "Calling deactivateObject for key " + paramKey);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 148 */       AOMEntry aOMEntry = this.activeObjectMap.get(paramKey);
/* 149 */       if (aOMEntry == null) {
/* 150 */         throw new ObjectNotActive();
/*     */       }
/* 152 */       Servant servant = this.activeObjectMap.getServant(aOMEntry);
/* 153 */       if (servant == null) {
/* 154 */         throw new ObjectNotActive();
/*     */       }
/* 156 */       if (this.orb.poaDebugFlag) {
/* 157 */         System.out.println("Deactivating object " + servant + " with POA " + this.poa);
/*     */       }
/*     */       
/* 160 */       deactivateHelper(paramKey, aOMEntry, servant);
/*     */       
/* 162 */       return servant;
/*     */     } finally {
/* 164 */       if (this.orb.poaDebugFlag) {
/* 165 */         ORBUtility.dprint(this, "Exiting deactivateObject");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] servantToId(Servant paramServant) throws ServantNotActive, WrongPolicy {
/* 175 */     if (!this.isUnique && !this.isImplicit) {
/* 176 */       throw new WrongPolicy();
/*     */     }
/* 178 */     if (this.isUnique) {
/* 179 */       ActiveObjectMap.Key key = this.activeObjectMap.getKey(paramServant);
/* 180 */       if (key != null) {
/* 181 */         return key.id;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 186 */     if (this.isImplicit) {
/*     */       try {
/* 188 */         byte[] arrayOfByte = newSystemId();
/* 189 */         activateObject(arrayOfByte, paramServant);
/* 190 */         return arrayOfByte;
/* 191 */       } catch (ObjectAlreadyActive objectAlreadyActive) {
/*     */         
/* 193 */         throw this.poa.invocationWrapper().servantToIdOaa(objectAlreadyActive);
/* 194 */       } catch (ServantAlreadyActive servantAlreadyActive) {
/* 195 */         throw this.poa.invocationWrapper().servantToIdSaa(servantAlreadyActive);
/* 196 */       } catch (WrongPolicy wrongPolicy) {
/* 197 */         throw this.poa.invocationWrapper().servantToIdWp(wrongPolicy);
/*     */       } 
/*     */     }
/* 200 */     throw new ServantNotActive();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POAPolicyMediatorBase_R.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */