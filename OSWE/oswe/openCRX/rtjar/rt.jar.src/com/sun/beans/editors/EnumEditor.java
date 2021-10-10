/*     */ package com.sun.beans.editors;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyEditor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EnumEditor
/*     */   implements PropertyEditor
/*     */ {
/*  46 */   private final List<PropertyChangeListener> listeners = new ArrayList<>();
/*     */   
/*     */   private final Class type;
/*     */   
/*     */   private final String[] tags;
/*     */   private Object value;
/*     */   
/*     */   public EnumEditor(Class<Object> paramClass) {
/*  54 */     Object[] arrayOfObject = paramClass.getEnumConstants();
/*  55 */     if (arrayOfObject == null) {
/*  56 */       throw new IllegalArgumentException("Unsupported " + paramClass);
/*     */     }
/*  58 */     this.type = paramClass;
/*  59 */     this.tags = new String[arrayOfObject.length];
/*  60 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*  61 */       this.tags[b] = ((Enum<Enum>)arrayOfObject[b]).name();
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  66 */     return this.value;
/*     */   } public void setValue(Object paramObject) {
/*     */     Object object;
/*     */     PropertyChangeListener[] arrayOfPropertyChangeListener;
/*  70 */     if (paramObject != null && !this.type.isInstance(paramObject)) {
/*  71 */       throw new IllegalArgumentException("Unsupported value: " + paramObject);
/*     */     }
/*     */ 
/*     */     
/*  75 */     synchronized (this.listeners) {
/*  76 */       object = this.value;
/*  77 */       this.value = paramObject;
/*     */       
/*  79 */       if ((paramObject == null) ? (object == null) : paramObject.equals(object)) {
/*     */         return;
/*     */       }
/*  82 */       int i = this.listeners.size();
/*  83 */       if (i == 0) {
/*     */         return;
/*     */       }
/*  86 */       arrayOfPropertyChangeListener = this.listeners.<PropertyChangeListener>toArray(new PropertyChangeListener[i]);
/*     */     } 
/*  88 */     PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(this, null, object, paramObject);
/*  89 */     for (PropertyChangeListener propertyChangeListener : arrayOfPropertyChangeListener) {
/*  90 */       propertyChangeListener.propertyChange(propertyChangeEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAsText() {
/*  95 */     return (this.value != null) ? ((Enum)this.value)
/*  96 */       .name() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAsText(String paramString) {
/* 101 */     setValue((paramString != null) ? 
/* 102 */         Enum.<Enum>valueOf(this.type, paramString) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getTags() {
/* 107 */     return (String[])this.tags.clone();
/*     */   }
/*     */   
/*     */   public String getJavaInitializationString() {
/* 111 */     String str = getAsText();
/* 112 */     return (str != null) ? (this.type
/* 113 */       .getName() + '.' + str) : "null";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPaintable() {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintValue(Graphics paramGraphics, Rectangle paramRectangle) {}
/*     */   
/*     */   public boolean supportsCustomEditor() {
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public Component getCustomEditor() {
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 133 */     synchronized (this.listeners) {
/* 134 */       this.listeners.add(paramPropertyChangeListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 139 */     synchronized (this.listeners) {
/* 140 */       this.listeners.remove(paramPropertyChangeListener);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/editors/EnumEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */