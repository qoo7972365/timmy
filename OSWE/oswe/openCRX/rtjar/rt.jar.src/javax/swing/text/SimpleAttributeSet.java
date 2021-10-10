/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleAttributeSet
/*     */   implements MutableAttributeSet, Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -6631553454711782652L;
/*  58 */   public static final AttributeSet EMPTY = new EmptyAttributeSet();
/*     */   
/*  60 */   private transient LinkedHashMap<Object, Object> table = new LinkedHashMap<>(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleAttributeSet() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleAttributeSet(AttributeSet paramAttributeSet) {
/*  74 */     addAttributes(paramAttributeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  84 */     return this.table.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeCount() {
/*  93 */     return this.table.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefined(Object paramObject) {
/* 103 */     return this.table.containsKey(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqual(AttributeSet paramAttributeSet) {
/* 113 */     return (getAttributeCount() == paramAttributeSet.getAttributeCount() && 
/* 114 */       containsAttributes(paramAttributeSet));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet copyAttributes() {
/* 123 */     return (AttributeSet)clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<?> getAttributeNames() {
/* 132 */     return Collections.enumeration(this.table.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute(Object paramObject) {
/* 142 */     Object object = this.table.get(paramObject);
/* 143 */     if (object == null) {
/* 144 */       AttributeSet attributeSet = getResolveParent();
/* 145 */       if (attributeSet != null) {
/* 146 */         object = attributeSet.getAttribute(paramObject);
/*     */       }
/*     */     } 
/* 149 */     return object;
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
/*     */   public boolean containsAttribute(Object paramObject1, Object paramObject2) {
/* 161 */     return paramObject2.equals(getAttribute(paramObject1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAttributes(AttributeSet paramAttributeSet) {
/* 172 */     boolean bool = true;
/*     */     
/* 174 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 175 */     while (bool && enumeration.hasMoreElements()) {
/* 176 */       Object object = enumeration.nextElement();
/* 177 */       bool = paramAttributeSet.getAttribute(object).equals(getAttribute(object));
/*     */     } 
/*     */     
/* 180 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(Object paramObject1, Object paramObject2) {
/* 190 */     this.table.put(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttributes(AttributeSet paramAttributeSet) {
/* 199 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 200 */     while (enumeration.hasMoreElements()) {
/* 201 */       Object object = enumeration.nextElement();
/* 202 */       addAttribute(object, paramAttributeSet.getAttribute(object));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAttribute(Object paramObject) {
/* 212 */     this.table.remove(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAttributes(Enumeration<?> paramEnumeration) {
/* 221 */     while (paramEnumeration.hasMoreElements()) {
/* 222 */       removeAttribute(paramEnumeration.nextElement());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAttributes(AttributeSet paramAttributeSet) {
/* 231 */     if (paramAttributeSet == this) {
/* 232 */       this.table.clear();
/*     */     } else {
/*     */       
/* 235 */       Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 236 */       while (enumeration.hasMoreElements()) {
/* 237 */         Object object1 = enumeration.nextElement();
/* 238 */         Object object2 = paramAttributeSet.getAttribute(object1);
/* 239 */         if (object2.equals(getAttribute(object1))) {
/* 240 */           removeAttribute(object1);
/*     */         }
/*     */       } 
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
/*     */   public AttributeSet getResolveParent() {
/* 255 */     return (AttributeSet)this.table.get(StyleConstants.ResolveAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResolveParent(AttributeSet paramAttributeSet) {
/* 264 */     addAttribute(StyleConstants.ResolveAttribute, paramAttributeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     Object object;
/*     */     try {
/* 277 */       object = super.clone();
/* 278 */       ((SimpleAttributeSet)object).table = (LinkedHashMap<Object, Object>)this.table.clone();
/* 279 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 280 */       object = null;
/*     */     } 
/* 282 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 290 */     return this.table.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 302 */     if (this == paramObject) {
/* 303 */       return true;
/*     */     }
/* 305 */     if (paramObject instanceof AttributeSet) {
/* 306 */       AttributeSet attributeSet = (AttributeSet)paramObject;
/* 307 */       return isEqual(attributeSet);
/*     */     } 
/* 309 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 318 */     String str = "";
/* 319 */     Enumeration<?> enumeration = getAttributeNames();
/* 320 */     while (enumeration.hasMoreElements()) {
/* 321 */       Object object1 = enumeration.nextElement();
/* 322 */       Object object2 = getAttribute(object1);
/* 323 */       if (object2 instanceof AttributeSet) {
/*     */         
/* 325 */         str = str + object1 + "=**AttributeSet** "; continue;
/*     */       } 
/* 327 */       str = str + object1 + "=" + object2 + " ";
/*     */     } 
/*     */     
/* 330 */     return str;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 334 */     paramObjectOutputStream.defaultWriteObject();
/* 335 */     StyleContext.writeAttributeSet(paramObjectOutputStream, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 340 */     paramObjectInputStream.defaultReadObject();
/* 341 */     this.table = new LinkedHashMap<>(3);
/* 342 */     StyleContext.readAttributeSet(paramObjectInputStream, this);
/*     */   }
/*     */ 
/*     */   
/*     */   static class EmptyAttributeSet
/*     */     implements AttributeSet, Serializable
/*     */   {
/*     */     static final long serialVersionUID = -8714803568785904228L;
/*     */     
/*     */     public int getAttributeCount() {
/* 352 */       return 0;
/*     */     }
/*     */     public boolean isDefined(Object param1Object) {
/* 355 */       return false;
/*     */     }
/*     */     public boolean isEqual(AttributeSet param1AttributeSet) {
/* 358 */       return (param1AttributeSet.getAttributeCount() == 0);
/*     */     }
/*     */     public AttributeSet copyAttributes() {
/* 361 */       return this;
/*     */     }
/*     */     public Object getAttribute(Object param1Object) {
/* 364 */       return null;
/*     */     }
/*     */     public Enumeration getAttributeNames() {
/* 367 */       return Collections.emptyEnumeration();
/*     */     }
/*     */     public boolean containsAttribute(Object param1Object1, Object param1Object2) {
/* 370 */       return false;
/*     */     }
/*     */     public boolean containsAttributes(AttributeSet param1AttributeSet) {
/* 373 */       return (param1AttributeSet.getAttributeCount() == 0);
/*     */     }
/*     */     public AttributeSet getResolveParent() {
/* 376 */       return null;
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 379 */       if (this == param1Object) {
/* 380 */         return true;
/*     */       }
/* 382 */       return (param1Object instanceof AttributeSet && ((AttributeSet)param1Object)
/* 383 */         .getAttributeCount() == 0);
/*     */     }
/*     */     public int hashCode() {
/* 386 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/SimpleAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */