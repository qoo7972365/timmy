/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.ctx.Continuation;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Vector;
/*     */ import javax.naming.LimitExceededException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.PartialResultException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.ldap.Control;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractLdapNamingEnumeration<T extends NameClassPair>
/*     */   implements NamingEnumeration<T>, ReferralEnumeration<T>
/*     */ {
/*     */   protected Name listArg;
/*     */   private boolean cleaned = false;
/*     */   private LdapResult res;
/*     */   private LdapClient enumClnt;
/*     */   private Continuation cont;
/*  49 */   private Vector<LdapEntry> entries = null;
/*  50 */   private int limit = 0;
/*  51 */   private int posn = 0;
/*     */   protected LdapCtx homeCtx;
/*  53 */   private LdapReferralException refEx = null;
/*  54 */   private NamingException errEx = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean more;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasMoreCalled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T nextElement() {
/*     */     try {
/* 106 */       return next();
/* 107 */     } catch (NamingException namingException) {
/*     */       
/* 109 */       cleanup();
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasMoreElements() {
/*     */     try {
/* 117 */       return hasMore();
/* 118 */     } catch (NamingException namingException) {
/*     */       
/* 120 */       cleanup();
/* 121 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getNextBatch() throws NamingException {
/* 130 */     this.res = this.homeCtx.getSearchReply(this.enumClnt, this.res);
/* 131 */     if (this.res == null) {
/* 132 */       this.limit = this.posn = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     this.entries = this.res.entries;
/* 137 */     this.limit = (this.entries == null) ? 0 : this.entries.size();
/* 138 */     this.posn = 0;
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (this.res.status != 0 || (this.res.status == 0 && this.res.referrals != null)) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 148 */         this.homeCtx.processReturnCode(this.res, this.listArg);
/*     */       }
/* 150 */       catch (LimitExceededException|PartialResultException limitExceededException) {
/* 151 */         setNamingException(limitExceededException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (this.res.refEx != null) {
/* 158 */       if (this.refEx == null) {
/* 159 */         this.refEx = this.res.refEx;
/*     */       } else {
/* 161 */         this.refEx = this.refEx.appendUnprocessedReferrals(this.res.refEx);
/*     */       } 
/* 163 */       this.res.refEx = null;
/*     */     } 
/*     */     
/* 166 */     if (this.res.resControls != null)
/* 167 */       this.homeCtx.respCtls = this.res.resControls; 
/*     */   }
/*     */   
/*     */   AbstractLdapNamingEnumeration(LdapCtx paramLdapCtx, LdapResult paramLdapResult, Name paramName, Continuation paramContinuation) throws NamingException {
/* 171 */     this.more = true;
/* 172 */     this.hasMoreCalled = false; if (paramLdapResult.status != 0 && paramLdapResult.status != 4 && paramLdapResult.status != 3 && paramLdapResult.status != 11 && paramLdapResult.status != 10 && paramLdapResult.status != 9) {
/*     */       NamingException namingException = new NamingException(LdapClient.getErrorMessage(paramLdapResult.status, paramLdapResult.errorMessage)); throw paramContinuation.fillInException(namingException);
/*     */     }  this.res = paramLdapResult; this.entries = paramLdapResult.entries; this.limit = (this.entries == null) ? 0 : this.entries.size(); this.listArg = paramName;
/*     */     this.cont = paramContinuation;
/*     */     if (paramLdapResult.refEx != null)
/*     */       this.refEx = paramLdapResult.refEx; 
/*     */     this.homeCtx = paramLdapCtx;
/*     */     paramLdapCtx.incEnumCount();
/* 180 */     this.enumClnt = paramLdapCtx.clnt; } public final boolean hasMore() throws NamingException { if (this.hasMoreCalled) {
/* 181 */       return this.more;
/*     */     }
/*     */     
/* 184 */     this.hasMoreCalled = true;
/*     */     
/* 186 */     if (!this.more) {
/* 187 */       return false;
/*     */     }
/* 189 */     return this.more = hasMoreImpl(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T next() throws NamingException {
/* 199 */     if (!this.hasMoreCalled) {
/* 200 */       hasMore();
/*     */     }
/* 202 */     this.hasMoreCalled = false;
/* 203 */     return nextImpl();
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
/*     */   private boolean hasMoreImpl() throws NamingException {
/* 216 */     if (this.posn == this.limit) {
/* 217 */       getNextBatch();
/*     */     }
/*     */ 
/*     */     
/* 221 */     if (this.posn < this.limit) {
/* 222 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 227 */       return hasMoreReferrals();
/*     */     }
/* 229 */     catch (LdapReferralException|LimitExceededException|PartialResultException ldapReferralException) {
/*     */ 
/*     */       
/* 232 */       cleanup();
/* 233 */       throw ldapReferralException;
/*     */     }
/* 235 */     catch (NamingException namingException) {
/* 236 */       cleanup();
/* 237 */       PartialResultException partialResultException = new PartialResultException();
/* 238 */       partialResultException.setRootCause(namingException);
/* 239 */       throw partialResultException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T nextImpl() throws NamingException {
/*     */     try {
/* 249 */       return nextAux();
/* 250 */     } catch (NamingException namingException) {
/* 251 */       cleanup();
/* 252 */       throw this.cont.fillInException(namingException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private T nextAux() throws NamingException {
/* 257 */     if (this.posn == this.limit) {
/* 258 */       getNextBatch();
/*     */     }
/*     */     
/* 261 */     if (this.posn >= this.limit) {
/* 262 */       cleanup();
/* 263 */       throw new NoSuchElementException("invalid enumeration handle");
/*     */     } 
/*     */     
/* 266 */     LdapEntry ldapEntry = this.entries.elementAt(this.posn++);
/*     */ 
/*     */     
/* 269 */     return createItem(ldapEntry.DN, ldapEntry.attributes, ldapEntry.respCtls);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String getAtom(String paramString) {
/*     */     try {
/* 276 */       LdapName ldapName = new LdapName(paramString);
/* 277 */       return ldapName.get(ldapName.size() - 1);
/* 278 */     } catch (NamingException namingException) {
/* 279 */       return paramString;
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
/*     */   public void appendUnprocessedReferrals(LdapReferralException paramLdapReferralException) {
/* 292 */     if (this.refEx != null) {
/* 293 */       this.refEx = this.refEx.appendUnprocessedReferrals(paramLdapReferralException);
/*     */     } else {
/* 295 */       this.refEx = paramLdapReferralException.appendUnprocessedReferrals(this.refEx);
/*     */     } 
/*     */   }
/*     */   
/*     */   final void setNamingException(NamingException paramNamingException) {
/* 300 */     this.errEx = paramNamingException;
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
/*     */   protected final boolean hasMoreReferrals() throws NamingException {
/* 313 */     if (this.refEx != null && (this.refEx
/* 314 */       .hasMoreReferrals() || this.refEx
/* 315 */       .hasMoreReferralExceptions())) {
/*     */       
/* 317 */       if (this.homeCtx.handleReferrals == 2) {
/* 318 */         throw (NamingException)this.refEx.fillInStackTrace();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 325 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)this.refEx.getReferralContext(this.homeCtx.envprops, this.homeCtx.reqCtls);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 330 */           update(getReferredResults(ldapReferralContext));
/*     */           
/*     */           break;
/* 333 */         } catch (LdapReferralException ldapReferralException) {
/*     */ 
/*     */           
/* 336 */           if (this.errEx == null) {
/* 337 */             this.errEx = ldapReferralException.getNamingException();
/*     */           }
/* 339 */           this.refEx = ldapReferralException;
/*     */         
/*     */         }
/*     */         finally {
/*     */           
/* 344 */           ldapReferralContext.close();
/*     */         } 
/*     */       } 
/* 347 */       return hasMoreImpl();
/*     */     } 
/*     */     
/* 350 */     cleanup();
/*     */     
/* 352 */     if (this.errEx != null) {
/* 353 */       throw this.errEx;
/*     */     }
/* 355 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(AbstractLdapNamingEnumeration<? extends NameClassPair> paramAbstractLdapNamingEnumeration) {
/* 365 */     this.homeCtx.decEnumCount();
/*     */ 
/*     */     
/* 368 */     this.homeCtx = paramAbstractLdapNamingEnumeration.homeCtx;
/* 369 */     this.enumClnt = paramAbstractLdapNamingEnumeration.enumClnt;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     paramAbstractLdapNamingEnumeration.homeCtx = null;
/*     */ 
/*     */     
/* 377 */     this.posn = paramAbstractLdapNamingEnumeration.posn;
/* 378 */     this.limit = paramAbstractLdapNamingEnumeration.limit;
/* 379 */     this.res = paramAbstractLdapNamingEnumeration.res;
/* 380 */     this.entries = paramAbstractLdapNamingEnumeration.entries;
/* 381 */     this.refEx = paramAbstractLdapNamingEnumeration.refEx;
/* 382 */     this.listArg = paramAbstractLdapNamingEnumeration.listArg;
/*     */   }
/*     */   
/*     */   protected final void finalize() {
/* 386 */     cleanup();
/*     */   }
/*     */   
/*     */   protected final void cleanup() {
/* 390 */     if (this.cleaned)
/*     */       return; 
/* 392 */     if (this.enumClnt != null) {
/* 393 */       this.enumClnt.clearSearchReply(this.res, this.homeCtx.reqCtls);
/*     */     }
/*     */     
/* 396 */     this.enumClnt = null;
/* 397 */     this.cleaned = true;
/* 398 */     if (this.homeCtx != null) {
/* 399 */       this.homeCtx.decEnumCount();
/* 400 */       this.homeCtx = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void close() {
/* 406 */     cleanup();
/*     */   }
/*     */   
/*     */   protected abstract T createItem(String paramString, Attributes paramAttributes, Vector<Control> paramVector) throws NamingException;
/*     */   
/*     */   protected abstract AbstractLdapNamingEnumeration<? extends NameClassPair> getReferredResults(LdapReferralContext paramLdapReferralContext) throws NamingException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/AbstractLdapNamingEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */