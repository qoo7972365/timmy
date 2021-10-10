/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.spi.extension.CopyObjectPolicy;
/*     */ import com.sun.corba.se.spi.extension.ServantCachingPolicy;
/*     */ import com.sun.corba.se.spi.extension.ZeroPortPolicy;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.PortableServer.IdAssignmentPolicy;
/*     */ import org.omg.PortableServer.IdUniquenessPolicy;
/*     */ import org.omg.PortableServer.ImplicitActivationPolicy;
/*     */ import org.omg.PortableServer.LifespanPolicy;
/*     */ import org.omg.PortableServer.POAPackage.InvalidPolicy;
/*     */ import org.omg.PortableServer.RequestProcessingPolicy;
/*     */ import org.omg.PortableServer.ServantRetentionPolicy;
/*     */ import org.omg.PortableServer.ThreadPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Policies
/*     */ {
/*     */   private static final int MIN_POA_POLICY_ID = 16;
/*     */   private static final int MAX_POA_POLICY_ID = 22;
/*     */   private static final int POLICY_TABLE_SIZE = 7;
/*     */   int defaultObjectCopierFactoryId;
/*  59 */   private HashMap policyMap = new HashMap<>();
/*     */   
/*  61 */   public static final Policies defaultPolicies = new Policies();
/*     */ 
/*     */   
/*  64 */   public static final Policies rootPOAPolicies = new Policies(0, 0, 0, 1, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] poaPolicyValues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPolicyValue(int paramInt) {
/*  78 */     return this.poaPolicyValues[paramInt - 16];
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPolicyValue(int paramInt1, int paramInt2) {
/*  83 */     this.poaPolicyValues[paramInt1 - 16] = paramInt2;
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
/*     */   private Policies(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  95 */     this.poaPolicyValues = new int[] { paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policies() {
/* 106 */     this(0, 0, 0, 1, 1, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     StringBuffer stringBuffer = new StringBuffer();
/* 117 */     stringBuffer.append("Policies[");
/* 118 */     boolean bool = true;
/* 119 */     Iterator<E> iterator = this.policyMap.values().iterator();
/* 120 */     while (iterator.hasNext()) {
/* 121 */       if (bool) {
/* 122 */         bool = false;
/*     */       } else {
/* 124 */         stringBuffer.append(",");
/*     */       } 
/* 126 */       stringBuffer.append(iterator.next().toString());
/*     */     } 
/* 128 */     stringBuffer.append("]");
/* 129 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPOAPolicyValue(Policy paramPolicy) {
/* 137 */     if (paramPolicy instanceof ThreadPolicy)
/* 138 */       return ((ThreadPolicy)paramPolicy).value().value(); 
/* 139 */     if (paramPolicy instanceof LifespanPolicy)
/* 140 */       return ((LifespanPolicy)paramPolicy).value().value(); 
/* 141 */     if (paramPolicy instanceof IdUniquenessPolicy)
/* 142 */       return ((IdUniquenessPolicy)paramPolicy).value().value(); 
/* 143 */     if (paramPolicy instanceof IdAssignmentPolicy)
/* 144 */       return ((IdAssignmentPolicy)paramPolicy).value().value(); 
/* 145 */     if (paramPolicy instanceof ServantRetentionPolicy)
/* 146 */       return ((ServantRetentionPolicy)paramPolicy).value().value(); 
/* 147 */     if (paramPolicy instanceof RequestProcessingPolicy)
/* 148 */       return ((RequestProcessingPolicy)paramPolicy).value().value(); 
/* 149 */     if (paramPolicy instanceof ImplicitActivationPolicy) {
/* 150 */       return ((ImplicitActivationPolicy)paramPolicy).value().value();
/*     */     }
/* 152 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForPolicyError(BitSet paramBitSet) throws InvalidPolicy {
/* 160 */     for (short s = 0; s < paramBitSet.length(); s = (short)(s + 1)) {
/* 161 */       if (paramBitSet.get(s)) {
/* 162 */         throw new InvalidPolicy(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToErrorSet(Policy[] paramArrayOfPolicy, int paramInt, BitSet paramBitSet) {
/* 171 */     for (byte b = 0; b < paramArrayOfPolicy.length; b++) {
/* 172 */       if (paramArrayOfPolicy[b].policy_type() == paramInt) {
/* 173 */         paramBitSet.set(b);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Policies(Policy[] paramArrayOfPolicy, int paramInt) throws InvalidPolicy {
/* 184 */     this();
/*     */     
/* 186 */     this.defaultObjectCopierFactoryId = paramInt;
/*     */     
/* 188 */     if (paramArrayOfPolicy == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 193 */     BitSet bitSet = new BitSet(paramArrayOfPolicy.length);
/*     */     short s;
/* 195 */     for (s = 0; s < paramArrayOfPolicy.length; s = (short)(s + 1)) {
/* 196 */       Policy policy1 = paramArrayOfPolicy[s];
/* 197 */       int i = getPOAPolicyValue(policy1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       Integer integer = new Integer(policy1.policy_type());
/* 203 */       Policy policy2 = (Policy)this.policyMap.get(integer);
/* 204 */       if (policy2 == null) {
/* 205 */         this.policyMap.put(integer, policy1);
/*     */       }
/* 207 */       if (i >= 0) {
/* 208 */         setPolicyValue(integer.intValue(), i);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 213 */         if (policy2 != null && 
/* 214 */           getPOAPolicyValue(policy2) != i) {
/* 215 */           bitSet.set(s);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (!retainServants() && useActiveMapOnly()) {
/* 223 */       addToErrorSet(paramArrayOfPolicy, 21, bitSet);
/*     */       
/* 225 */       addToErrorSet(paramArrayOfPolicy, 22, bitSet);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (isImplicitlyActivated()) {
/* 231 */       if (!retainServants()) {
/* 232 */         addToErrorSet(paramArrayOfPolicy, 20, bitSet);
/*     */         
/* 234 */         addToErrorSet(paramArrayOfPolicy, 21, bitSet);
/*     */       } 
/*     */ 
/*     */       
/* 238 */       if (!isSystemAssignedIds()) {
/* 239 */         addToErrorSet(paramArrayOfPolicy, 20, bitSet);
/*     */         
/* 241 */         addToErrorSet(paramArrayOfPolicy, 19, bitSet);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 246 */     checkForPolicyError(bitSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public Policy get_effective_policy(int paramInt) {
/* 251 */     Integer integer = new Integer(paramInt);
/* 252 */     return (Policy)this.policyMap.get(integer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isOrbControlledThreads() {
/* 258 */     return (getPolicyValue(16) == 0);
/*     */   }
/*     */   
/*     */   public final boolean isSingleThreaded() {
/* 262 */     return (getPolicyValue(16) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isTransient() {
/* 268 */     return (getPolicyValue(17) == 0);
/*     */   }
/*     */   
/*     */   public final boolean isPersistent() {
/* 272 */     return (getPolicyValue(17) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isUniqueIds() {
/* 278 */     return (getPolicyValue(18) == 0);
/*     */   }
/*     */   
/*     */   public final boolean isMultipleIds() {
/* 282 */     return (getPolicyValue(18) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isUserAssignedIds() {
/* 288 */     return (getPolicyValue(19) == 0);
/*     */   }
/*     */   
/*     */   public final boolean isSystemAssignedIds() {
/* 292 */     return (getPolicyValue(19) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean retainServants() {
/* 298 */     return (getPolicyValue(21) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean useActiveMapOnly() {
/* 304 */     return (getPolicyValue(22) == 0);
/*     */   }
/*     */   
/*     */   public final boolean useDefaultServant() {
/* 308 */     return (getPolicyValue(22) == 1);
/*     */   }
/*     */   
/*     */   public final boolean useServantManager() {
/* 312 */     return (getPolicyValue(22) == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isImplicitlyActivated() {
/* 318 */     return (getPolicyValue(20) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int servantCachingLevel() {
/* 325 */     Integer integer = new Integer(1398079488);
/* 326 */     ServantCachingPolicy servantCachingPolicy = (ServantCachingPolicy)this.policyMap.get(integer);
/* 327 */     if (servantCachingPolicy == null) {
/* 328 */       return 0;
/*     */     }
/* 330 */     return servantCachingPolicy.getType();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean forceZeroPort() {
/* 335 */     Integer integer = new Integer(1398079489);
/* 336 */     ZeroPortPolicy zeroPortPolicy = (ZeroPortPolicy)this.policyMap.get(integer);
/* 337 */     if (zeroPortPolicy == null) {
/* 338 */       return false;
/*     */     }
/* 340 */     return zeroPortPolicy.forceZeroPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getCopierId() {
/* 345 */     Integer integer = new Integer(1398079490);
/* 346 */     CopyObjectPolicy copyObjectPolicy = (CopyObjectPolicy)this.policyMap.get(integer);
/* 347 */     if (copyObjectPolicy != null) {
/* 348 */       return copyObjectPolicy.getValue();
/*     */     }
/* 350 */     return this.defaultObjectCopierFactoryId;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/Policies.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */