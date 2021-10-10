/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.ctx.Continuation;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.InterruptedNamingException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.event.EventContext;
/*     */ import javax.naming.event.NamingEvent;
/*     */ import javax.naming.event.NamingExceptionEvent;
/*     */ import javax.naming.event.NamingListener;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.HasControls;
/*     */ import javax.naming.ldap.LdapName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NamingEventNotifier
/*     */   implements Runnable
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private Vector<NamingListener> namingListeners;
/*     */   private Thread worker;
/*     */   private LdapCtx context;
/*     */   private EventContext eventSrc;
/*     */   private EventSupport support;
/*     */   private NamingEnumeration<SearchResult> results;
/*     */   NotifierArgs info;
/*     */   
/*     */   NamingEventNotifier(EventSupport paramEventSupport, LdapCtx paramLdapCtx, NotifierArgs paramNotifierArgs, NamingListener paramNamingListener) throws NamingException {
/*     */     PersistentSearchControl persistentSearchControl;
/*  65 */     this.info = paramNotifierArgs;
/*  66 */     this.support = paramEventSupport;
/*     */ 
/*     */     
/*     */     try {
/*  70 */       persistentSearchControl = new PersistentSearchControl(paramNotifierArgs.mask, true, true, true);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  75 */     catch (IOException iOException) {
/*  76 */       NamingException namingException = new NamingException("Problem creating persistent search control");
/*     */       
/*  78 */       namingException.setRootCause(iOException);
/*  79 */       throw namingException;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     this.context = (LdapCtx)paramLdapCtx.newInstance(new Control[] { persistentSearchControl });
/*  84 */     this.eventSrc = paramLdapCtx;
/*     */     
/*  86 */     this.namingListeners = new Vector<>();
/*  87 */     this.namingListeners.addElement(paramNamingListener);
/*     */     
/*  89 */     this.worker = Obj.helper.createThread(this);
/*  90 */     this.worker.setDaemon(true);
/*  91 */     this.worker.start();
/*     */   }
/*     */ 
/*     */   
/*     */   void addNamingListener(NamingListener paramNamingListener) {
/*  96 */     this.namingListeners.addElement(paramNamingListener);
/*     */   }
/*     */ 
/*     */   
/*     */   void removeNamingListener(NamingListener paramNamingListener) {
/* 101 */     this.namingListeners.removeElement(paramNamingListener);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasNamingListeners() {
/* 106 */     return (this.namingListeners.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 116 */       Continuation continuation = new Continuation();
/* 117 */       continuation.setError(this, this.info.name);
/*     */       
/* 119 */       Name name = (this.info.name == null || this.info.name.equals("")) ? new CompositeName() : (new CompositeName()).add(this.info.name);
/*     */       
/* 121 */       this.results = this.context.searchAux(name, this.info.filter, this.info.controls, true, false, continuation);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       ((LdapSearchEnumeration)this.results)
/* 128 */         .setStartName(this.context.currentParsedDN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       while (this.results.hasMore()) {
/* 136 */         SearchResult searchResult = this.results.next();
/*     */         
/* 138 */         Control[] arrayOfControl = (searchResult instanceof HasControls) ? ((HasControls)searchResult).getControls() : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 146 */         if (arrayOfControl != null) {
/* 147 */           byte b = 0; if (b < arrayOfControl.length)
/*     */           {
/*     */             
/* 150 */             if (arrayOfControl[b] instanceof EntryChangeResponseControl) {
/* 151 */               EntryChangeResponseControl entryChangeResponseControl = (EntryChangeResponseControl)arrayOfControl[b];
/* 152 */               long l = entryChangeResponseControl.getChangeNumber();
/* 153 */               switch (entryChangeResponseControl.getChangeType()) {
/*     */                 case 1:
/* 155 */                   fireObjectAdded(searchResult, l);
/*     */                 
/*     */                 case 2:
/* 158 */                   fireObjectRemoved(searchResult, l);
/*     */                 
/*     */                 case 4:
/* 161 */                   fireObjectChanged(searchResult, l);
/*     */                 
/*     */                 case 8:
/* 164 */                   fireObjectRenamed(searchResult, entryChangeResponseControl.getPreviousDN(), l);
/*     */               } 
/*     */ 
/*     */ 
/*     */             
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 173 */     } catch (InterruptedNamingException interruptedNamingException) {
/*     */     
/* 175 */     } catch (NamingException namingException) {
/*     */       
/* 177 */       fireNamingException(namingException);
/*     */ 
/*     */       
/* 180 */       this.support.removeDeadNotifier(this.info);
/*     */     } finally {
/* 182 */       cleanup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanup() {
/*     */     try {
/* 191 */       if (this.results != null) {
/*     */         
/* 193 */         this.results.close();
/* 194 */         this.results = null;
/*     */       } 
/* 196 */       if (this.context != null) {
/*     */         
/* 198 */         this.context.close();
/* 199 */         this.context = null;
/*     */       } 
/* 201 */     } catch (NamingException namingException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stop() {
/* 210 */     if (this.worker != null) {
/* 211 */       this.worker.interrupt();
/* 212 */       this.worker = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireObjectAdded(Binding paramBinding, long paramLong) {
/* 220 */     if (this.namingListeners == null || this.namingListeners.size() == 0) {
/*     */       return;
/*     */     }
/* 223 */     NamingEvent namingEvent = new NamingEvent(this.eventSrc, 0, paramBinding, null, new Long(paramLong));
/*     */     
/* 225 */     this.support.queueEvent(namingEvent, this.namingListeners);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireObjectRemoved(Binding paramBinding, long paramLong) {
/* 232 */     if (this.namingListeners == null || this.namingListeners.size() == 0) {
/*     */       return;
/*     */     }
/* 235 */     NamingEvent namingEvent = new NamingEvent(this.eventSrc, 1, null, paramBinding, new Long(paramLong));
/*     */     
/* 237 */     this.support.queueEvent(namingEvent, this.namingListeners);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireObjectChanged(Binding paramBinding, long paramLong) {
/* 244 */     if (this.namingListeners == null || this.namingListeners.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     Binding binding = new Binding(paramBinding.getName(), null, paramBinding.isRelative());
/*     */     
/* 250 */     NamingEvent namingEvent = new NamingEvent(this.eventSrc, 3, paramBinding, binding, new Long(paramLong));
/*     */     
/* 252 */     this.support.queueEvent(namingEvent, this.namingListeners);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireObjectRenamed(Binding paramBinding, String paramString, long paramLong) {
/* 259 */     if (this.namingListeners == null || this.namingListeners.size() == 0) {
/*     */       return;
/*     */     }
/* 262 */     Binding binding = null;
/*     */     try {
/* 264 */       LdapName ldapName = new LdapName(paramString);
/* 265 */       if (ldapName.startsWith(this.context.currentParsedDN)) {
/* 266 */         String str = ldapName.getSuffix(this.context.currentParsedDN.size()).toString();
/* 267 */         binding = new Binding(str, null);
/*     */       } 
/* 269 */     } catch (NamingException namingException) {}
/*     */     
/* 271 */     if (binding == null) {
/* 272 */       binding = new Binding(paramString, null, false);
/*     */     }
/*     */     
/* 275 */     NamingEvent namingEvent = new NamingEvent(this.eventSrc, 2, paramBinding, binding, new Long(paramLong));
/*     */     
/* 277 */     this.support.queueEvent(namingEvent, this.namingListeners);
/*     */   }
/*     */   
/*     */   private void fireNamingException(NamingException paramNamingException) {
/* 281 */     if (this.namingListeners == null || this.namingListeners.size() == 0) {
/*     */       return;
/*     */     }
/* 284 */     NamingExceptionEvent namingExceptionEvent = new NamingExceptionEvent(this.eventSrc, paramNamingException);
/* 285 */     this.support.queueEvent(namingExceptionEvent, this.namingListeners);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/NamingEventNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */