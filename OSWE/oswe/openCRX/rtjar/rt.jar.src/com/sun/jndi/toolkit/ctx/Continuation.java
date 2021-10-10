/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.ResolveResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Continuation
/*     */   extends ResolveResult
/*     */ {
/*     */   protected Name starter;
/*  54 */   protected Object followingLink = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected Hashtable<?, ?> environment = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean continuing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected Context resolvedContext = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected Name relativeResolvedName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8162530656132624308L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Continuation() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Continuation(Name paramName, Hashtable<?, ?> paramHashtable) {
/* 100 */     this.starter = paramName;
/* 101 */     this
/* 102 */       .environment = (paramHashtable == null) ? null : (Hashtable<?, ?>)paramHashtable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContinue() {
/* 113 */     return this.continuing;
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
/*     */   public void setSuccess() {
/* 129 */     this.continuing = false;
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
/*     */   public NamingException fillInException(NamingException paramNamingException) {
/* 145 */     paramNamingException.setRemainingName(this.remainingName);
/* 146 */     paramNamingException.setResolvedObj(this.resolvedObj);
/*     */     
/* 148 */     if (this.starter == null || this.starter.isEmpty()) {
/* 149 */       paramNamingException.setResolvedName(null);
/* 150 */     } else if (this.remainingName == null) {
/* 151 */       paramNamingException.setResolvedName(this.starter);
/*     */     } else {
/* 153 */       paramNamingException.setResolvedName(this.starter
/* 154 */           .getPrefix(this.starter.size() - this.remainingName
/* 155 */             .size()));
/*     */     } 
/* 157 */     if (paramNamingException instanceof CannotProceedException) {
/* 158 */       CannotProceedException cannotProceedException = (CannotProceedException)paramNamingException;
/*     */       
/* 160 */       Hashtable<?, ?> hashtable = (this.environment == null) ? new Hashtable<>(11) : (Hashtable)this.environment.clone();
/* 161 */       cannotProceedException.setEnvironment(hashtable);
/* 162 */       cannotProceedException.setAltNameCtx(this.resolvedContext);
/* 163 */       cannotProceedException.setAltName(this.relativeResolvedName);
/*     */     } 
/*     */     
/* 166 */     return paramNamingException;
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
/*     */   public void setErrorNNS(Object paramObject, Name paramName) {
/* 186 */     Name name = (Name)paramName.clone();
/*     */     try {
/* 188 */       name.add("");
/* 189 */     } catch (InvalidNameException invalidNameException) {}
/*     */ 
/*     */     
/* 192 */     setErrorAux(paramObject, name);
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
/*     */   public void setErrorNNS(Object paramObject, String paramString) {
/* 204 */     CompositeName compositeName = new CompositeName();
/*     */     try {
/* 206 */       if (paramString != null && !paramString.equals("")) {
/* 207 */         compositeName.add(paramString);
/*     */       }
/* 209 */       compositeName.add("");
/* 210 */     } catch (InvalidNameException invalidNameException) {}
/*     */ 
/*     */     
/* 213 */     setErrorAux(paramObject, compositeName);
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
/*     */   public void setError(Object paramObject, Name paramName) {
/* 231 */     if (paramName != null) {
/* 232 */       this.remainingName = (Name)paramName.clone();
/*     */     } else {
/* 234 */       this.remainingName = null;
/*     */     } 
/* 236 */     setErrorAux(paramObject, this.remainingName);
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
/*     */   public void setError(Object paramObject, String paramString) {
/* 249 */     CompositeName compositeName = new CompositeName();
/* 250 */     if (paramString != null && !paramString.equals("")) {
/*     */       try {
/* 252 */         compositeName.add(paramString);
/* 253 */       } catch (InvalidNameException invalidNameException) {}
/*     */     }
/*     */ 
/*     */     
/* 257 */     setErrorAux(paramObject, compositeName);
/*     */   }
/*     */   
/*     */   private void setErrorAux(Object paramObject, Name paramName) {
/* 261 */     this.remainingName = paramName;
/* 262 */     this.resolvedObj = paramObject;
/* 263 */     this.continuing = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setContinueAux(Object paramObject, Name paramName1, Context paramContext, Name paramName2) {
/* 268 */     if (paramObject instanceof javax.naming.LinkRef) {
/* 269 */       setContinueLink(paramObject, paramName1, paramContext, paramName2);
/*     */     } else {
/* 271 */       this.remainingName = paramName2;
/* 272 */       this.resolvedObj = paramObject;
/*     */       
/* 274 */       this.relativeResolvedName = paramName1;
/* 275 */       this.resolvedContext = paramContext;
/*     */       
/* 277 */       this.continuing = true;
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
/*     */   public void setContinueNNS(Object paramObject, Name paramName, Context paramContext) {
/* 297 */     CompositeName compositeName = new CompositeName();
/*     */     
/* 299 */     setContinue(paramObject, paramName, paramContext, PartialCompositeContext._NNS_NAME);
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
/*     */   public void setContinueNNS(Object paramObject, String paramString, Context paramContext) {
/* 311 */     CompositeName compositeName = new CompositeName();
/*     */     try {
/* 313 */       compositeName.add(paramString);
/* 314 */     } catch (NamingException namingException) {}
/*     */     
/* 316 */     setContinue(paramObject, compositeName, paramContext, PartialCompositeContext._NNS_NAME);
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
/*     */   public void setContinue(Object paramObject, Name paramName, Context paramContext) {
/* 337 */     setContinueAux(paramObject, paramName, paramContext, (Name)PartialCompositeContext._EMPTY_NAME
/* 338 */         .clone());
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
/*     */   public void setContinue(Object paramObject, Name paramName1, Context paramContext, Name paramName2) {
/* 358 */     if (paramName2 != null) {
/* 359 */       this.remainingName = (Name)paramName2.clone();
/*     */     } else {
/* 361 */       this.remainingName = new CompositeName();
/*     */     } 
/* 363 */     setContinueAux(paramObject, paramName1, paramContext, this.remainingName);
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
/*     */   public void setContinue(Object paramObject, String paramString1, Context paramContext, String paramString2) {
/* 377 */     CompositeName compositeName1 = new CompositeName();
/* 378 */     if (!paramString1.equals("")) {
/*     */       try {
/* 380 */         compositeName1.add(paramString1);
/* 381 */       } catch (NamingException namingException) {}
/*     */     }
/*     */     
/* 384 */     CompositeName compositeName2 = new CompositeName();
/* 385 */     if (!paramString2.equals("")) {
/*     */       try {
/* 387 */         compositeName2.add(paramString2);
/* 388 */       } catch (NamingException namingException) {}
/*     */     }
/*     */ 
/*     */     
/* 392 */     setContinueAux(paramObject, compositeName1, paramContext, compositeName2);
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
/*     */   @Deprecated
/*     */   public void setContinue(Object paramObject1, Object paramObject2) {
/* 405 */     setContinue(paramObject1, (Name)null, (Context)paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setContinueLink(Object paramObject, Name paramName1, Context paramContext, Name paramName2) {
/* 415 */     this.followingLink = paramObject;
/*     */     
/* 417 */     this.remainingName = paramName2;
/* 418 */     this.resolvedObj = paramContext;
/*     */     
/* 420 */     this.relativeResolvedName = PartialCompositeContext._EMPTY_NAME;
/* 421 */     this.resolvedContext = paramContext;
/*     */     
/* 423 */     this.continuing = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 427 */     if (this.remainingName != null) {
/* 428 */       return this.starter.toString() + "; remainingName: '" + this.remainingName + "'";
/*     */     }
/* 430 */     return this.starter.toString();
/*     */   }
/*     */   
/*     */   public String toString(boolean paramBoolean) {
/* 434 */     if (!paramBoolean || this.resolvedObj == null)
/* 435 */       return toString(); 
/* 436 */     return toString() + "; resolvedObj: " + this.resolvedObj + "; relativeResolvedName: " + this.relativeResolvedName + "; resolvedContext: " + this.resolvedContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/Continuation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */