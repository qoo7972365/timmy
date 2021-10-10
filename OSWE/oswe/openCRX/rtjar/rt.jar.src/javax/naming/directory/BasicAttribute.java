/*     */ package javax.naming.directory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Vector;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicAttribute
/*     */   implements Attribute
/*     */ {
/*     */   protected String attrID;
/*     */   protected transient Vector<Object> values;
/*     */   protected boolean ordered = false;
/*     */   private static final long serialVersionUID = 6743528196119291326L;
/*     */   
/*     */   public Object clone() {
/*     */     BasicAttribute basicAttribute;
/*     */     try {
/*  98 */       basicAttribute = (BasicAttribute)super.clone();
/*  99 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 100 */       basicAttribute = new BasicAttribute(this.attrID, this.ordered);
/*     */     } 
/* 102 */     basicAttribute.values = (Vector<Object>)this.values.clone();
/* 103 */     return basicAttribute;
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
/*     */   public boolean equals(Object paramObject) {
/* 133 */     if (paramObject != null && paramObject instanceof Attribute) {
/* 134 */       Attribute attribute = (Attribute)paramObject;
/*     */ 
/*     */       
/* 137 */       if (isOrdered() != attribute.isOrdered()) {
/* 138 */         return false;
/*     */       }
/*     */       int i;
/* 141 */       if (this.attrID.equals(attribute.getID()) && (
/* 142 */         i = size()) == attribute.size()) {
/*     */         try {
/* 144 */           if (isOrdered()) {
/*     */             
/* 146 */             for (byte b = 0; b < i; b++) {
/* 147 */               if (!valueEquals(get(b), attribute.get(b))) {
/* 148 */                 return false;
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             
/* 153 */             NamingEnumeration<?> namingEnumeration = attribute.getAll();
/* 154 */             while (namingEnumeration.hasMoreElements()) {
/* 155 */               if (find(namingEnumeration.nextElement()) < 0)
/* 156 */                 return false; 
/*     */             } 
/*     */           } 
/* 159 */         } catch (NamingException namingException) {
/* 160 */           return false;
/*     */         } 
/* 162 */         return true;
/*     */       } 
/*     */     } 
/* 165 */     return false;
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
/*     */   public int hashCode() {
/* 183 */     int i = this.attrID.hashCode();
/* 184 */     int j = this.values.size();
/*     */     
/* 186 */     for (byte b = 0; b < j; b++) {
/* 187 */       Object object = this.values.elementAt(b);
/* 188 */       if (object != null) {
/* 189 */         if (object.getClass().isArray()) {
/*     */           
/* 191 */           int k = Array.getLength(object);
/* 192 */           for (byte b1 = 0; b1 < k; b1++) {
/* 193 */             Object object1 = Array.get(object, b1);
/* 194 */             if (object1 != null) {
/* 195 */               i += object1.hashCode();
/*     */             }
/*     */           } 
/*     */         } else {
/* 199 */           i += object.hashCode();
/*     */         } 
/*     */       }
/*     */     } 
/* 203 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 214 */     StringBuffer stringBuffer = new StringBuffer(this.attrID + ": ");
/* 215 */     if (this.values.size() == 0) {
/* 216 */       stringBuffer.append("No values");
/*     */     } else {
/* 218 */       boolean bool = true;
/* 219 */       for (Enumeration enumeration = this.values.elements(); enumeration.hasMoreElements(); ) {
/* 220 */         if (!bool)
/* 221 */           stringBuffer.append(", "); 
/* 222 */         stringBuffer.append(enumeration.nextElement());
/* 223 */         bool = false;
/*     */       } 
/*     */     } 
/* 226 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAttribute(String paramString) {
/* 235 */     this(paramString, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAttribute(String paramString, Object paramObject) {
/* 246 */     this(paramString, paramObject, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicAttribute(String paramString, boolean paramBoolean) {
/* 257 */     this.attrID = paramString;
/* 258 */     this.values = new Vector();
/* 259 */     this.ordered = paramBoolean;
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
/*     */   public BasicAttribute(String paramString, Object paramObject, boolean paramBoolean) {
/* 273 */     this(paramString, paramBoolean);
/* 274 */     this.values.addElement(paramObject);
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
/*     */   public NamingEnumeration<?> getAll() throws NamingException {
/* 286 */     return new ValuesEnumImpl();
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
/*     */   public Object get() throws NamingException {
/* 298 */     if (this.values.size() == 0) {
/* 299 */       throw new NoSuchElementException("Attribute " + 
/* 300 */           getID() + " has no value");
/*     */     }
/* 302 */     return this.values.elementAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 307 */     return this.values.size();
/*     */   }
/*     */   
/*     */   public String getID() {
/* 311 */     return this.attrID;
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
/*     */   public boolean contains(Object paramObject) {
/* 325 */     return (find(paramObject) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int find(Object paramObject) {
/* 332 */     if (paramObject == null)
/* 333 */     { int i = this.values.size();
/* 334 */       for (byte b = 0; b < i; b++) {
/* 335 */         if (this.values.elementAt(b) == null)
/* 336 */           return b; 
/*     */       }  }
/* 338 */     else { Class<?> clazz; if ((clazz = paramObject.getClass()).isArray()) {
/* 339 */         int i = this.values.size();
/*     */         
/* 341 */         for (byte b = 0; b < i; b++) {
/* 342 */           Object object = this.values.elementAt(b);
/* 343 */           if (object != null && clazz == object.getClass() && 
/* 344 */             arrayEquals(paramObject, object))
/* 345 */             return b; 
/*     */         } 
/*     */       } else {
/* 348 */         return this.values.indexOf(paramObject, 0);
/*     */       }  }
/* 350 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean valueEquals(Object paramObject1, Object paramObject2) {
/* 358 */     if (paramObject1 == paramObject2) {
/* 359 */       return true;
/*     */     }
/* 361 */     if (paramObject1 == null) {
/* 362 */       return false;
/*     */     }
/* 364 */     if (paramObject1.getClass().isArray() && paramObject2
/* 365 */       .getClass().isArray()) {
/* 366 */       return arrayEquals(paramObject1, paramObject2);
/*     */     }
/* 368 */     return paramObject1.equals(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean arrayEquals(Object paramObject1, Object paramObject2) {
/*     */     int i;
/* 377 */     if ((i = Array.getLength(paramObject1)) != Array.getLength(paramObject2)) {
/* 378 */       return false;
/*     */     }
/* 380 */     for (byte b = 0; b < i; b++) {
/* 381 */       Object object1 = Array.get(paramObject1, b);
/* 382 */       Object object2 = Array.get(paramObject2, b);
/* 383 */       if (object1 == null || object2 == null) {
/* 384 */         if (object1 != object2)
/* 385 */           return false; 
/* 386 */       } else if (!object1.equals(object2)) {
/* 387 */         return false;
/*     */       } 
/*     */     } 
/* 390 */     return true;
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
/*     */   public boolean add(Object paramObject) {
/* 403 */     if (isOrdered() || find(paramObject) < 0) {
/* 404 */       this.values.addElement(paramObject);
/* 405 */       return true;
/*     */     } 
/* 407 */     return false;
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
/*     */   public boolean remove(Object paramObject) {
/* 424 */     int i = find(paramObject);
/* 425 */     if (i >= 0) {
/* 426 */       this.values.removeElementAt(i);
/* 427 */       return true;
/*     */     } 
/* 429 */     return false;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 433 */     this.values.setSize(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOrdered() {
/* 439 */     return this.ordered;
/*     */   }
/*     */   
/*     */   public Object get(int paramInt) throws NamingException {
/* 443 */     return this.values.elementAt(paramInt);
/*     */   }
/*     */   
/*     */   public Object remove(int paramInt) {
/* 447 */     Object object = this.values.elementAt(paramInt);
/* 448 */     this.values.removeElementAt(paramInt);
/* 449 */     return object;
/*     */   }
/*     */   
/*     */   public void add(int paramInt, Object paramObject) {
/* 453 */     if (!isOrdered() && contains(paramObject)) {
/* 454 */       throw new IllegalStateException("Cannot add duplicate to unordered attribute");
/*     */     }
/*     */     
/* 457 */     this.values.insertElementAt(paramObject, paramInt);
/*     */   }
/*     */   
/*     */   public Object set(int paramInt, Object paramObject) {
/* 461 */     if (!isOrdered() && contains(paramObject)) {
/* 462 */       throw new IllegalStateException("Cannot add duplicate to unordered attribute");
/*     */     }
/*     */ 
/*     */     
/* 466 */     Object object = this.values.elementAt(paramInt);
/* 467 */     this.values.setElementAt(paramObject, paramInt);
/* 468 */     return object;
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
/*     */   public DirContext getAttributeSyntaxDefinition() throws NamingException {
/* 480 */     throw new OperationNotSupportedException("attribute syntax");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getAttributeDefinition() throws NamingException {
/* 490 */     throw new OperationNotSupportedException("attribute definition");
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 504 */     paramObjectOutputStream.defaultWriteObject();
/* 505 */     paramObjectOutputStream.writeInt(this.values.size());
/* 506 */     for (byte b = 0; b < this.values.size(); b++) {
/* 507 */       paramObjectOutputStream.writeObject(this.values.elementAt(b));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 516 */     paramObjectInputStream.defaultReadObject();
/* 517 */     int i = paramObjectInputStream.readInt();
/* 518 */     this.values = new Vector(Math.min(1024, i));
/* 519 */     while (--i >= 0) {
/* 520 */       this.values.addElement(paramObjectInputStream.readObject());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class ValuesEnumImpl
/*     */     implements NamingEnumeration<Object>
/*     */   {
/* 529 */     Enumeration<Object> list = BasicAttribute.this.values.elements();
/*     */ 
/*     */     
/*     */     public boolean hasMoreElements() {
/* 533 */       return this.list.hasMoreElements();
/*     */     }
/*     */     
/*     */     public Object nextElement() {
/* 537 */       return this.list.nextElement();
/*     */     }
/*     */     
/*     */     public Object next() throws NamingException {
/* 541 */       return this.list.nextElement();
/*     */     }
/*     */     
/*     */     public boolean hasMore() throws NamingException {
/* 545 */       return this.list.hasMoreElements();
/*     */     }
/*     */     
/*     */     public void close() throws NamingException {
/* 549 */       this.list = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/directory/BasicAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */