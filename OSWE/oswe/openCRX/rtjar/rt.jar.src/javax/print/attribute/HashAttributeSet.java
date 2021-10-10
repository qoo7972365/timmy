/*     */ package javax.print.attribute;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashAttributeSet
/*     */   implements AttributeSet, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5311560590283707917L;
/*     */   private Class myInterface;
/*  57 */   private transient HashMap attrMap = new HashMap<>();
/*     */ 
/*     */ 
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
/*  70 */     paramObjectOutputStream.defaultWriteObject();
/*  71 */     Attribute[] arrayOfAttribute = toArray();
/*  72 */     paramObjectOutputStream.writeInt(arrayOfAttribute.length);
/*  73 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*  74 */       paramObjectOutputStream.writeObject(arrayOfAttribute[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/*  84 */     paramObjectInputStream.defaultReadObject();
/*  85 */     this.attrMap = new HashMap<>();
/*  86 */     int i = paramObjectInputStream.readInt();
/*     */     
/*  88 */     for (byte b = 0; b < i; b++) {
/*  89 */       Attribute attribute = (Attribute)paramObjectInputStream.readObject();
/*  90 */       add(attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashAttributeSet() {
/*  98 */     this(Attribute.class);
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
/*     */   public HashAttributeSet(Attribute paramAttribute) {
/* 111 */     this(paramAttribute, Attribute.class);
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
/*     */   public HashAttributeSet(Attribute[] paramArrayOfAttribute) {
/* 131 */     this(paramArrayOfAttribute, Attribute.class);
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
/*     */   public HashAttributeSet(AttributeSet paramAttributeSet) {
/* 143 */     this(paramAttributeSet, Attribute.class);
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
/*     */   protected HashAttributeSet(Class<?> paramClass) {
/* 157 */     if (paramClass == null) {
/* 158 */       throw new NullPointerException("null interface");
/*     */     }
/* 160 */     this.myInterface = paramClass;
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
/*     */   
/*     */   protected HashAttributeSet(Attribute paramAttribute, Class<?> paramClass) {
/* 182 */     if (paramClass == null) {
/* 183 */       throw new NullPointerException("null interface");
/*     */     }
/* 185 */     this.myInterface = paramClass;
/* 186 */     add(paramAttribute);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HashAttributeSet(Attribute[] paramArrayOfAttribute, Class<?> paramClass) {
/* 215 */     if (paramClass == null) {
/* 216 */       throw new NullPointerException("null interface");
/*     */     }
/* 218 */     this.myInterface = paramClass;
/* 219 */     byte b1 = (paramArrayOfAttribute == null) ? 0 : paramArrayOfAttribute.length;
/* 220 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 221 */       add(paramArrayOfAttribute[b2]);
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
/*     */ 
/*     */   
/*     */   protected HashAttributeSet(AttributeSet paramAttributeSet, Class<?> paramClass) {
/* 243 */     this.myInterface = paramClass;
/* 244 */     if (paramAttributeSet != null) {
/* 245 */       Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 246 */       byte b1 = (arrayOfAttribute == null) ? 0 : arrayOfAttribute.length;
/* 247 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 248 */         add(arrayOfAttribute[b2]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute get(Class<?> paramClass) {
/* 277 */     return (Attribute)this.attrMap
/* 278 */       .get(
/* 279 */         AttributeSetUtilities.verifyAttributeCategory(paramClass, Attribute.class));
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
/*     */ 
/*     */   
/*     */   public boolean add(Attribute paramAttribute) {
/* 302 */     Object object = this.attrMap.put(paramAttribute.getCategory(), 
/*     */         
/* 304 */         AttributeSetUtilities.verifyAttributeValue(paramAttribute, this.myInterface));
/* 305 */     return !paramAttribute.equals(object);
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
/*     */   public boolean remove(Class<?> paramClass) {
/* 325 */     return (paramClass != null && 
/*     */ 
/*     */       
/* 328 */       AttributeSetUtilities.verifyAttributeCategory(paramClass, Attribute.class) != null && this.attrMap
/* 329 */       .remove(paramClass) != null);
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
/*     */   public boolean remove(Attribute paramAttribute) {
/* 348 */     return (paramAttribute != null && this.attrMap
/*     */       
/* 350 */       .remove(paramAttribute.getCategory()) != null);
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
/*     */   public boolean containsKey(Class<?> paramClass) {
/* 364 */     return (paramClass != null && 
/*     */ 
/*     */       
/* 367 */       AttributeSetUtilities.verifyAttributeCategory(paramClass, Attribute.class) != null && this.attrMap
/* 368 */       .get(paramClass) != null);
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
/*     */   public boolean containsValue(Attribute paramAttribute) {
/* 382 */     return (paramAttribute != null && paramAttribute instanceof Attribute && paramAttribute
/*     */ 
/*     */       
/* 385 */       .equals(this.attrMap.get(paramAttribute.getCategory())));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(AttributeSet paramAttributeSet) {
/* 420 */     Attribute[] arrayOfAttribute = paramAttributeSet.toArray();
/* 421 */     boolean bool = false;
/* 422 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/*     */       
/* 424 */       Attribute attribute = AttributeSetUtilities.verifyAttributeValue(arrayOfAttribute[b], this.myInterface);
/*     */       
/* 426 */       Object object = this.attrMap.put(attribute.getCategory(), attribute);
/* 427 */       bool = (!attribute.equals(object) || bool) ? true : false;
/*     */     } 
/* 429 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 440 */     return this.attrMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute[] toArray() {
/* 449 */     Attribute[] arrayOfAttribute = new Attribute[size()];
/* 450 */     this.attrMap.values().toArray((Object[])arrayOfAttribute);
/* 451 */     return arrayOfAttribute;
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
/*     */   public void clear() {
/* 463 */     this.attrMap.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 472 */     return this.attrMap.isEmpty();
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
/*     */   public boolean equals(Object paramObject) {
/* 490 */     if (paramObject == null || !(paramObject instanceof AttributeSet)) {
/* 491 */       return false;
/*     */     }
/*     */     
/* 494 */     AttributeSet attributeSet = (AttributeSet)paramObject;
/* 495 */     if (attributeSet.size() != size()) {
/* 496 */       return false;
/*     */     }
/*     */     
/* 499 */     Attribute[] arrayOfAttribute = toArray();
/* 500 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 501 */       if (!attributeSet.containsValue(arrayOfAttribute[b])) {
/* 502 */         return false;
/*     */       }
/*     */     } 
/* 505 */     return true;
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
/*     */   public int hashCode() {
/* 520 */     int i = 0;
/* 521 */     Attribute[] arrayOfAttribute = toArray();
/* 522 */     for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 523 */       i += arrayOfAttribute[b].hashCode();
/*     */     }
/* 525 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/HashAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */