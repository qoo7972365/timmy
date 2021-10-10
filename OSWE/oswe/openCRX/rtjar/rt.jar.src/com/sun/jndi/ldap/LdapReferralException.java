/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.ReferralException;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.LdapReferralException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LdapReferralException
/*     */   extends LdapReferralException
/*     */ {
/*     */   private static final long serialVersionUID = 627059076356906399L;
/*     */   private int handleReferrals;
/*     */   private Hashtable<?, ?> envprops;
/*     */   private String nextName;
/*     */   private Control[] reqCtls;
/*  78 */   private Vector<?> referrals = null;
/*  79 */   private int referralIndex = 0;
/*  80 */   private int referralCount = 0;
/*     */   private boolean foundEntry = false;
/*     */   private boolean skipThisReferral = false;
/*  83 */   private int hopCount = 1;
/*  84 */   private NamingException errorEx = null;
/*  85 */   private String newRdn = null;
/*     */   private boolean debug = false;
/*  87 */   LdapReferralException nextReferralEx = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LdapReferralException(Name paramName1, Object paramObject, Name paramName2, String paramString1, Hashtable<?, ?> paramHashtable, String paramString2, int paramInt, Control[] paramArrayOfControl) {
/* 106 */     super(paramString1);
/*     */     
/* 108 */     if (this.debug) {
/* 109 */       System.out.println("LdapReferralException constructor");
/*     */     }
/* 111 */     setResolvedName(paramName1);
/* 112 */     setResolvedObj(paramObject);
/* 113 */     setRemainingName(paramName2);
/* 114 */     this.envprops = paramHashtable;
/* 115 */     this.nextName = paramString2;
/* 116 */     this.handleReferrals = paramInt;
/*     */ 
/*     */     
/* 119 */     this.reqCtls = (paramInt == 1 || paramInt == 4) ? paramArrayOfControl : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context getReferralContext() throws NamingException {
/* 129 */     return getReferralContext(this.envprops, (Control[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context getReferralContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/* 138 */     return getReferralContext(paramHashtable, (Control[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Context getReferralContext(Hashtable<?, ?> paramHashtable, Control[] paramArrayOfControl) throws NamingException {
/* 148 */     if (this.debug) {
/* 149 */       System.out.println("LdapReferralException.getReferralContext");
/*     */     }
/* 151 */     LdapReferralContext ldapReferralContext = new LdapReferralContext(this, paramHashtable, paramArrayOfControl, this.reqCtls, this.nextName, this.skipThisReferral, this.handleReferrals);
/*     */ 
/*     */ 
/*     */     
/* 155 */     ldapReferralContext.setHopCount(this.hopCount + 1);
/*     */     
/* 157 */     if (this.skipThisReferral) {
/* 158 */       this.skipThisReferral = false;
/*     */     }
/* 160 */     return ldapReferralContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getReferralInfo() {
/* 167 */     if (this.debug) {
/* 168 */       System.out.println("LdapReferralException.getReferralInfo");
/* 169 */       System.out.println("  referralIndex=" + this.referralIndex);
/*     */     } 
/*     */     
/* 172 */     if (hasMoreReferrals()) {
/* 173 */       return this.referrals.elementAt(this.referralIndex);
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void retryReferral() {
/* 183 */     if (this.debug) {
/* 184 */       System.out.println("LdapReferralException.retryReferral");
/*     */     }
/* 186 */     if (this.referralIndex > 0) {
/* 187 */       this.referralIndex--;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean skipReferral() {
/* 195 */     if (this.debug) {
/* 196 */       System.out.println("LdapReferralException.skipReferral");
/*     */     }
/* 198 */     this.skipThisReferral = true;
/*     */ 
/*     */     
/*     */     try {
/* 202 */       getNextReferral();
/* 203 */     } catch (ReferralException referralException) {}
/*     */ 
/*     */ 
/*     */     
/* 207 */     return (hasMoreReferrals() || hasMoreReferralExceptions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setReferralInfo(Vector<?> paramVector, boolean paramBoolean) {
/* 217 */     if (this.debug) {
/* 218 */       System.out.println("LdapReferralException.setReferralInfo");
/*     */     }
/* 220 */     this.referrals = paramVector;
/* 221 */     this.referralCount = (paramVector == null) ? 0 : paramVector.size();
/*     */     
/* 223 */     if (this.debug) {
/* 224 */       if (paramVector != null) {
/* 225 */         for (byte b = 0; b < this.referralCount; b++) {
/* 226 */           System.out.println("  [" + b + "] " + paramVector.elementAt(b));
/*     */         }
/*     */       } else {
/* 229 */         System.out.println("setReferralInfo : referrals == null");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getNextReferral() throws ReferralException {
/* 240 */     if (this.debug) {
/* 241 */       System.out.println("LdapReferralException.getNextReferral");
/*     */     }
/* 243 */     if (hasMoreReferrals())
/* 244 */       return (String)this.referrals.elementAt(this.referralIndex++); 
/* 245 */     if (hasMoreReferralExceptions()) {
/* 246 */       throw this.nextReferralEx;
/*     */     }
/* 248 */     return null;
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
/*     */   LdapReferralException appendUnprocessedReferrals(LdapReferralException paramLdapReferralException) {
/* 260 */     if (this.debug) {
/* 261 */       System.out.println("LdapReferralException.appendUnprocessedReferrals");
/*     */       
/* 263 */       dump();
/* 264 */       if (paramLdapReferralException != null) {
/* 265 */         paramLdapReferralException.dump();
/*     */       }
/*     */     } 
/*     */     
/* 269 */     LdapReferralException ldapReferralException1 = this;
/*     */     
/* 271 */     if (!ldapReferralException1.hasMoreReferrals()) {
/* 272 */       ldapReferralException1 = this.nextReferralEx;
/*     */       
/* 274 */       if (this.errorEx != null && ldapReferralException1 != null) {
/* 275 */         ldapReferralException1.setNamingException(this.errorEx);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 280 */     if (this == paramLdapReferralException) {
/* 281 */       return ldapReferralException1;
/*     */     }
/*     */     
/* 284 */     if (paramLdapReferralException != null && !paramLdapReferralException.hasMoreReferrals()) {
/* 285 */       paramLdapReferralException = paramLdapReferralException.nextReferralEx;
/*     */     }
/*     */     
/* 288 */     if (paramLdapReferralException == null) {
/* 289 */       return ldapReferralException1;
/*     */     }
/*     */ 
/*     */     
/* 293 */     LdapReferralException ldapReferralException2 = ldapReferralException1;
/* 294 */     while (ldapReferralException2.nextReferralEx != null) {
/* 295 */       ldapReferralException2 = ldapReferralException2.nextReferralEx;
/*     */     }
/* 297 */     ldapReferralException2.nextReferralEx = paramLdapReferralException;
/*     */     
/* 299 */     return ldapReferralException1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMoreReferrals() {
/* 308 */     if (this.debug) {
/* 309 */       System.out.println("LdapReferralException.hasMoreReferrals");
/*     */     }
/* 311 */     return (!this.foundEntry && this.referralIndex < this.referralCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasMoreReferralExceptions() {
/* 318 */     if (this.debug) {
/* 319 */       System.out.println("LdapReferralException.hasMoreReferralExceptions");
/*     */     }
/*     */     
/* 322 */     return (this.nextReferralEx != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setHopCount(int paramInt) {
/* 330 */     if (this.debug) {
/* 331 */       System.out.println("LdapReferralException.setHopCount");
/*     */     }
/* 333 */     this.hopCount = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNameResolved(boolean paramBoolean) {
/* 340 */     if (this.debug) {
/* 341 */       System.out.println("LdapReferralException.setNameResolved");
/*     */     }
/* 343 */     this.foundEntry = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNamingException(NamingException paramNamingException) {
/* 351 */     if (this.debug) {
/* 352 */       System.out.println("LdapReferralException.setNamingException");
/*     */     }
/* 354 */     if (this.errorEx == null) {
/* 355 */       paramNamingException.setRootCause(this);
/* 356 */       this.errorEx = paramNamingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getNewRdn() {
/* 364 */     if (this.debug) {
/* 365 */       System.out.println("LdapReferralException.getNewRdn");
/*     */     }
/* 367 */     return this.newRdn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNewRdn(String paramString) {
/* 375 */     if (this.debug) {
/* 376 */       System.out.println("LdapReferralException.setNewRdn");
/*     */     }
/* 378 */     this.newRdn = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NamingException getNamingException() {
/* 385 */     if (this.debug) {
/* 386 */       System.out.println("LdapReferralException.getNamingException");
/*     */     }
/* 388 */     return this.errorEx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dump() {
/* 397 */     System.out.println();
/* 398 */     System.out.println("LdapReferralException.dump");
/* 399 */     LdapReferralException ldapReferralException = this;
/* 400 */     while (ldapReferralException != null) {
/* 401 */       ldapReferralException.dumpState();
/* 402 */       ldapReferralException = ldapReferralException.nextReferralEx;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dumpState() {
/* 410 */     System.out.println("LdapReferralException.dumpState");
/* 411 */     System.out.println("  hashCode=" + hashCode());
/* 412 */     System.out.println("  foundEntry=" + this.foundEntry);
/* 413 */     System.out.println("  skipThisReferral=" + this.skipThisReferral);
/* 414 */     System.out.println("  referralIndex=" + this.referralIndex);
/*     */     
/* 416 */     if (this.referrals != null) {
/* 417 */       System.out.println("  referrals:");
/* 418 */       for (byte b = 0; b < this.referralCount; b++) {
/* 419 */         System.out.println("    [" + b + "] " + this.referrals.elementAt(b));
/*     */       }
/*     */     } else {
/* 422 */       System.out.println("  referrals=null");
/*     */     } 
/*     */     
/* 425 */     System.out.println("  errorEx=" + this.errorEx);
/*     */     
/* 427 */     if (this.nextReferralEx == null) {
/* 428 */       System.out.println("  nextRefEx=null");
/*     */     } else {
/* 430 */       System.out.println("  nextRefEx=" + this.nextReferralEx.hashCode());
/*     */     } 
/* 432 */     System.out.println();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapReferralException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */