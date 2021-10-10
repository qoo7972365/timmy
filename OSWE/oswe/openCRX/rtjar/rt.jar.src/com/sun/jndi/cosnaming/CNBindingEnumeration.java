/*     */ package com.sun.jndi.cosnaming;
/*     */ 
/*     */ import com.sun.jndi.toolkit.corba.CorbaUtils;
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.NamingManager;
/*     */ import org.omg.CosNaming.Binding;
/*     */ import org.omg.CosNaming.BindingIterator;
/*     */ import org.omg.CosNaming.BindingIteratorHolder;
/*     */ import org.omg.CosNaming.BindingListHolder;
/*     */ import org.omg.CosNaming.NameComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CNBindingEnumeration
/*     */   implements NamingEnumeration<Binding>
/*     */ {
/*     */   private static final int DEFAULT_BATCHSIZE = 100;
/*     */   private BindingListHolder _bindingList;
/*     */   private BindingIterator _bindingIter;
/*     */   private int counter;
/*  54 */   private int batchsize = 100;
/*     */ 
/*     */   
/*     */   private CNCtx _ctx;
/*     */   
/*     */   private Hashtable<?, ?> _env;
/*     */   
/*     */   private boolean more = false;
/*     */   
/*     */   private boolean isLookedUpCtx = false;
/*     */ 
/*     */   
/*     */   CNBindingEnumeration(CNCtx paramCNCtx, boolean paramBoolean, Hashtable<?, ?> paramHashtable) {
/*  67 */     String str = (paramHashtable != null) ? (String)paramHashtable.get("java.naming.batchsize") : null;
/*  68 */     if (str != null) {
/*     */       try {
/*  70 */         this.batchsize = Integer.parseInt(str);
/*  71 */       } catch (NumberFormatException numberFormatException) {
/*  72 */         throw new IllegalArgumentException("Batch size not numeric: " + str);
/*     */       } 
/*     */     }
/*  75 */     this._ctx = paramCNCtx;
/*  76 */     this._ctx.incEnumCount();
/*  77 */     this.isLookedUpCtx = paramBoolean;
/*  78 */     this._env = paramHashtable;
/*  79 */     this._bindingList = new BindingListHolder();
/*  80 */     BindingIteratorHolder bindingIteratorHolder = new BindingIteratorHolder();
/*     */ 
/*     */ 
/*     */     
/*  84 */     this._ctx._nc.list(0, this._bindingList, bindingIteratorHolder);
/*     */     
/*  86 */     this._bindingIter = bindingIteratorHolder.value;
/*     */ 
/*     */     
/*  89 */     if (this._bindingIter != null) {
/*  90 */       this.more = this._bindingIter.next_n(this.batchsize, this._bindingList);
/*     */     } else {
/*  92 */       this.more = false;
/*     */     } 
/*  94 */     this.counter = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Binding next() throws NamingException {
/* 103 */     if (this.more && this.counter >= this._bindingList.value.length) {
/* 104 */       getMore();
/*     */     }
/* 106 */     if (this.more && this.counter < this._bindingList.value.length) {
/* 107 */       Binding binding = this._bindingList.value[this.counter];
/* 108 */       this.counter++;
/* 109 */       return mapBinding(binding);
/*     */     } 
/* 111 */     throw new NoSuchElementException();
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
/*     */   public boolean hasMore() throws NamingException {
/* 125 */     return this.more ? ((this.counter < this._bindingList.value.length || getMore())) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMoreElements() {
/*     */     try {
/* 136 */       return hasMore();
/* 137 */     } catch (NamingException namingException) {
/* 138 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Binding nextElement() {
/*     */     try {
/* 150 */       return next();
/* 151 */     } catch (NamingException namingException) {
/* 152 */       throw new NoSuchElementException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/* 157 */     this.more = false;
/* 158 */     if (this._bindingIter != null) {
/* 159 */       this._bindingIter.destroy();
/* 160 */       this._bindingIter = null;
/*     */     } 
/* 162 */     if (this._ctx != null) {
/* 163 */       this._ctx.decEnumCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       if (this.isLookedUpCtx) {
/* 170 */         this._ctx.close();
/*     */       }
/* 172 */       this._ctx = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() {
/*     */     try {
/* 178 */       close();
/* 179 */     } catch (NamingException namingException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getMore() throws NamingException {
/*     */     try {
/* 189 */       this.more = this._bindingIter.next_n(this.batchsize, this._bindingList);
/* 190 */       this.counter = 0;
/* 191 */     } catch (Exception exception) {
/* 192 */       this.more = false;
/* 193 */       NamingException namingException = new NamingException("Problem getting binding list");
/*     */       
/* 195 */       namingException.setRootCause(exception);
/* 196 */       throw namingException;
/*     */     } 
/* 198 */     return this.more;
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
/*     */   private Binding mapBinding(Binding paramBinding) throws NamingException {
/* 212 */     Object object = this._ctx.callResolve(paramBinding.binding_name);
/*     */     
/* 214 */     Name name = CNNameParser.cosNameToName(paramBinding.binding_name);
/*     */ 
/*     */     
/*     */     try {
/* 218 */       if (CorbaUtils.isObjectFactoryTrusted(object)) {
/* 219 */         object = NamingManager.getObjectInstance(object, name, this._ctx, this._env);
/*     */       }
/* 221 */     } catch (NamingException namingException) {
/* 222 */       throw namingException;
/* 223 */     } catch (Exception exception) {
/* 224 */       NamingException namingException = new NamingException("problem generating object using object factory");
/*     */       
/* 226 */       namingException.setRootCause(exception);
/* 227 */       throw namingException;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 232 */     String str1 = name.toString();
/* 233 */     Binding binding = new Binding(str1, object);
/*     */     
/* 235 */     NameComponent[] arrayOfNameComponent = this._ctx.makeFullName(paramBinding.binding_name);
/* 236 */     String str2 = CNNameParser.cosNameToInsString(arrayOfNameComponent);
/* 237 */     binding.setNameInNamespace(str2);
/* 238 */     return binding;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/CNBindingEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */