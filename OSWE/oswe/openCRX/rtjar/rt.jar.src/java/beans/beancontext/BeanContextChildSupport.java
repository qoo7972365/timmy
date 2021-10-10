/*     */ package java.beans.beancontext;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.beans.VetoableChangeListener;
/*     */ import java.beans.VetoableChangeSupport;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanContextChildSupport
/*     */   implements BeanContextChild, BeanContextServicesListener, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 6328947014421475877L;
/*     */   public BeanContextChild beanContextChildPeer;
/*     */   protected PropertyChangeSupport pcSupport;
/*     */   protected VetoableChangeSupport vcSupport;
/*     */   protected transient BeanContext beanContext;
/*     */   protected transient boolean rejectedSetBCOnce;
/*     */   
/*     */   public BeanContextChildSupport() {
/*  71 */     this.beanContextChildPeer = this;
/*     */     
/*  73 */     this.pcSupport = new PropertyChangeSupport(this.beanContextChildPeer);
/*  74 */     this.vcSupport = new VetoableChangeSupport(this.beanContextChildPeer);
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
/*     */   public BeanContextChildSupport(BeanContextChild paramBeanContextChild) {
/*  87 */     this.beanContextChildPeer = (paramBeanContextChild != null) ? paramBeanContextChild : this;
/*     */     
/*  89 */     this.pcSupport = new PropertyChangeSupport(this.beanContextChildPeer);
/*  90 */     this.vcSupport = new VetoableChangeSupport(this.beanContextChildPeer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setBeanContext(BeanContext paramBeanContext) throws PropertyVetoException {
/* 101 */     if (paramBeanContext == this.beanContext)
/*     */       return; 
/* 103 */     BeanContext beanContext1 = this.beanContext;
/* 104 */     BeanContext beanContext2 = paramBeanContext;
/*     */     
/* 106 */     if (!this.rejectedSetBCOnce) {
/* 107 */       if (this.rejectedSetBCOnce = !validatePendingSetBeanContext(paramBeanContext)) {
/* 108 */         throw new PropertyVetoException("setBeanContext() change rejected:", new PropertyChangeEvent(this.beanContextChildPeer, "beanContext", beanContext1, beanContext2));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 115 */         fireVetoableChange("beanContext", beanContext1, beanContext2);
/*     */ 
/*     */       
/*     */       }
/* 119 */       catch (PropertyVetoException propertyVetoException) {
/* 120 */         this.rejectedSetBCOnce = true;
/*     */         
/* 122 */         throw propertyVetoException;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (this.beanContext != null) releaseBeanContextResources();
/*     */     
/* 128 */     this.beanContext = beanContext2;
/* 129 */     this.rejectedSetBCOnce = false;
/*     */     
/* 131 */     firePropertyChange("beanContext", beanContext1, beanContext2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (this.beanContext != null) initializeBeanContextResources();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized BeanContext getBeanContext() {
/* 145 */     return this.beanContext;
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
/*     */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 159 */     this.pcSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 176 */     this.pcSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 191 */     this.vcSupport.addVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*     */   public void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 208 */     this.vcSupport.removeVetoableChangeListener(paramString, paramVetoableChangeListener);
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
/*     */   public void serviceRevoked(BeanContextServiceRevokedEvent paramBeanContextServiceRevokedEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serviceAvailable(BeanContextServiceAvailableEvent paramBeanContextServiceAvailableEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanContextChild getBeanContextChildPeer() {
/* 238 */     return this.beanContextChildPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelegated() {
/* 245 */     return !equals(this.beanContextChildPeer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 255 */     this.pcSupport.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*     */   public void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2) throws PropertyVetoException {
/* 276 */     this.vcSupport.fireVetoableChange(paramString, paramObject1, paramObject2);
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
/*     */   public boolean validatePendingSetBeanContext(BeanContext paramBeanContext) {
/* 289 */     return true;
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
/*     */   protected void releaseBeanContextResources() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeBeanContextResources() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 324 */     if (!equals(this.beanContextChildPeer) && !(this.beanContextChildPeer instanceof Serializable)) {
/* 325 */       throw new IOException("BeanContextChildSupport beanContextChildPeer not Serializable");
/*     */     }
/*     */     
/* 328 */     paramObjectOutputStream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 340 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/beancontext/BeanContextChildSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */