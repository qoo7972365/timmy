/*     */ package com.sun.jndi.toolkit.dir;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.spi.DirectoryManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LazySearchEnumerationImpl
/*     */   implements NamingEnumeration<SearchResult>
/*     */ {
/*     */   private NamingEnumeration<Binding> candidates;
/*  53 */   private SearchResult nextMatch = null;
/*     */   
/*     */   private SearchControls cons;
/*     */   private AttrFilter filter;
/*     */   private Context context;
/*     */   private Hashtable<String, Object> env;
/*     */   private boolean useFactory = true;
/*     */   
/*     */   public LazySearchEnumerationImpl(NamingEnumeration<Binding> paramNamingEnumeration, AttrFilter paramAttrFilter, SearchControls paramSearchControls) throws NamingException {
/*  62 */     this.candidates = paramNamingEnumeration;
/*  63 */     this.filter = paramAttrFilter;
/*     */     
/*  65 */     if (paramSearchControls == null) {
/*  66 */       this.cons = new SearchControls();
/*     */     } else {
/*  68 */       this.cons = paramSearchControls;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazySearchEnumerationImpl(NamingEnumeration<Binding> paramNamingEnumeration, AttrFilter paramAttrFilter, SearchControls paramSearchControls, Context paramContext, Hashtable<String, Object> paramHashtable, boolean paramBoolean) throws NamingException {
/*  78 */     this.candidates = paramNamingEnumeration;
/*  79 */     this.filter = paramAttrFilter;
/*  80 */     this
/*  81 */       .env = (paramHashtable == null) ? null : (Hashtable<String, Object>)paramHashtable.clone();
/*  82 */     this.context = paramContext;
/*  83 */     this.useFactory = paramBoolean;
/*     */     
/*  85 */     if (paramSearchControls == null) {
/*  86 */       this.cons = new SearchControls();
/*     */     } else {
/*  88 */       this.cons = paramSearchControls;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LazySearchEnumerationImpl(NamingEnumeration<Binding> paramNamingEnumeration, AttrFilter paramAttrFilter, SearchControls paramSearchControls, Context paramContext, Hashtable<String, Object> paramHashtable) throws NamingException {
/*  96 */     this(paramNamingEnumeration, paramAttrFilter, paramSearchControls, paramContext, paramHashtable, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMore() throws NamingException {
/* 101 */     return (findNextMatch(false) != null);
/*     */   }
/*     */   
/*     */   public boolean hasMoreElements() {
/*     */     try {
/* 106 */       return hasMore();
/* 107 */     } catch (NamingException namingException) {
/* 108 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SearchResult nextElement() {
/*     */     try {
/* 114 */       return findNextMatch(true);
/* 115 */     } catch (NamingException namingException) {
/* 116 */       throw new NoSuchElementException(namingException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SearchResult next() throws NamingException {
/* 122 */     return findNextMatch(true);
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/* 126 */     if (this.candidates != null) {
/* 127 */       this.candidates.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private SearchResult findNextMatch(boolean paramBoolean) throws NamingException {
/* 133 */     if (this.nextMatch != null) {
/* 134 */       SearchResult searchResult = this.nextMatch;
/* 135 */       if (paramBoolean) {
/* 136 */         this.nextMatch = null;
/*     */       }
/* 138 */       return searchResult;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     while (this.candidates.hasMore()) {
/* 145 */       Binding binding = this.candidates.next();
/* 146 */       Object object = binding.getObject();
/* 147 */       if (object instanceof DirContext) {
/* 148 */         Attributes attributes = ((DirContext)object).getAttributes("");
/* 149 */         if (this.filter.check(attributes)) {
/* 150 */           if (!this.cons.getReturningObjFlag()) {
/* 151 */             object = null;
/* 152 */           } else if (this.useFactory) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */               
/* 158 */               CompositeName compositeName = (this.context != null) ? new CompositeName(binding.getName()) : null;
/* 159 */               object = DirectoryManager.getObjectInstance(object, compositeName, this.context, this.env, attributes);
/*     */             }
/* 161 */             catch (NamingException namingException) {
/* 162 */               throw namingException;
/* 163 */             } catch (Exception exception) {
/* 164 */               NamingException namingException = new NamingException("problem generating object using object factory");
/*     */               
/* 166 */               namingException.setRootCause(exception);
/* 167 */               throw namingException;
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 172 */           SearchResult searchResult = new SearchResult(binding.getName(), binding.getClassName(), object, SearchFilter.selectAttributes(attributes, this.cons
/* 173 */                 .getReturningAttributes()), true);
/*     */           
/* 175 */           if (!paramBoolean)
/* 176 */             this.nextMatch = searchResult; 
/* 177 */           return searchResult;
/*     */         } 
/*     */       } 
/*     */     } 
/* 181 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/LazySearchEnumerationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */