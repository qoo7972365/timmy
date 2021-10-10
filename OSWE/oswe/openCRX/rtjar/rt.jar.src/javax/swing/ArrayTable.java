/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ArrayTable
/*     */   implements Cloneable
/*     */ {
/*  47 */   private Object table = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int ARRAY_BOUNDARY = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void writeArrayTable(ObjectOutputStream paramObjectOutputStream, ArrayTable paramArrayTable) throws IOException {
/*     */     Object[] arrayOfObject;
/*  63 */     if (paramArrayTable == null || (arrayOfObject = paramArrayTable.getKeys(null)) == null) {
/*  64 */       paramObjectOutputStream.writeInt(0);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  70 */       byte b1 = 0;
/*     */       
/*  72 */       for (byte b2 = 0; b2 < arrayOfObject.length; b2++) {
/*  73 */         Object object = arrayOfObject[b2];
/*     */ 
/*     */         
/*  76 */         if ((object instanceof java.io.Serializable && paramArrayTable
/*  77 */           .get(object) instanceof java.io.Serializable) || (object instanceof ClientPropertyKey && ((ClientPropertyKey)object)
/*     */ 
/*     */ 
/*     */           
/*  81 */           .getReportValueNotSerializable())) {
/*     */           
/*  83 */           b1++;
/*     */         } else {
/*  85 */           arrayOfObject[b2] = null;
/*     */         } 
/*     */       } 
/*     */       
/*  89 */       paramObjectOutputStream.writeInt(b1);
/*  90 */       if (b1 > 0) {
/*  91 */         for (Object object : arrayOfObject) {
/*  92 */           if (object != null) {
/*  93 */             paramObjectOutputStream.writeObject(object);
/*  94 */             paramObjectOutputStream.writeObject(paramArrayTable.get(object));
/*  95 */             if (--b1 == 0) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(Object paramObject1, Object paramObject2) {
/* 109 */     if (this.table == null) {
/* 110 */       this.table = new Object[] { paramObject1, paramObject2 };
/*     */     } else {
/* 112 */       int i = size();
/* 113 */       if (i < 8) {
/* 114 */         if (containsKey(paramObject1)) {
/* 115 */           Object[] arrayOfObject = (Object[])this.table;
/* 116 */           for (byte b = 0; b < arrayOfObject.length - 1; b += 2) {
/* 117 */             if (arrayOfObject[b].equals(paramObject1)) {
/* 118 */               arrayOfObject[b + 1] = paramObject2;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 123 */           Object[] arrayOfObject1 = (Object[])this.table;
/* 124 */           int j = arrayOfObject1.length;
/* 125 */           Object[] arrayOfObject2 = new Object[j + 2];
/* 126 */           System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, j);
/*     */           
/* 128 */           arrayOfObject2[j] = paramObject1;
/* 129 */           arrayOfObject2[j + 1] = paramObject2;
/* 130 */           this.table = arrayOfObject2;
/*     */         } 
/*     */       } else {
/* 133 */         if (i == 8 && isArray()) {
/* 134 */           grow();
/*     */         }
/* 136 */         ((Hashtable<Object, Object>)this.table).put(paramObject1, paramObject2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object paramObject) {
/* 145 */     Object object = null;
/* 146 */     if (this.table != null) {
/* 147 */       if (isArray()) {
/* 148 */         Object[] arrayOfObject = (Object[])this.table;
/* 149 */         for (byte b = 0; b < arrayOfObject.length - 1; b += 2) {
/* 150 */           if (arrayOfObject[b].equals(paramObject)) {
/* 151 */             object = arrayOfObject[b + 1];
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 156 */         object = ((Hashtable)this.table).get(paramObject);
/*     */       } 
/*     */     }
/* 159 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*     */     int i;
/* 167 */     if (this.table == null)
/* 168 */       return 0; 
/* 169 */     if (isArray()) {
/* 170 */       i = ((Object[])this.table).length / 2;
/*     */     } else {
/* 172 */       i = ((Hashtable)this.table).size();
/*     */     } 
/* 174 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object paramObject) {
/* 181 */     boolean bool = false;
/* 182 */     if (this.table != null) {
/* 183 */       if (isArray()) {
/* 184 */         Object[] arrayOfObject = (Object[])this.table;
/* 185 */         for (byte b = 0; b < arrayOfObject.length - 1; b += 2) {
/* 186 */           if (arrayOfObject[b].equals(paramObject)) {
/* 187 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 192 */         bool = ((Hashtable)this.table).containsKey(paramObject);
/*     */       } 
/*     */     }
/* 195 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object paramObject) {
/* 203 */     Object object = null;
/* 204 */     if (paramObject == null) {
/* 205 */       return null;
/*     */     }
/* 207 */     if (this.table != null) {
/* 208 */       if (isArray()) {
/*     */         
/* 210 */         int i = -1;
/* 211 */         Object[] arrayOfObject = (Object[])this.table;
/* 212 */         for (int j = arrayOfObject.length - 2; j >= 0; j -= 2) {
/* 213 */           if (arrayOfObject[j].equals(paramObject)) {
/* 214 */             i = j;
/* 215 */             object = arrayOfObject[j + 1];
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         
/* 221 */         if (i != -1) {
/* 222 */           Object[] arrayOfObject1 = new Object[arrayOfObject.length - 2];
/*     */           
/* 224 */           System.arraycopy(arrayOfObject, 0, arrayOfObject1, 0, i);
/*     */ 
/*     */ 
/*     */           
/* 228 */           if (i < arrayOfObject1.length) {
/* 229 */             System.arraycopy(arrayOfObject, i + 2, arrayOfObject1, i, arrayOfObject1.length - i);
/*     */           }
/*     */           
/* 232 */           this.table = (arrayOfObject1.length == 0) ? null : arrayOfObject1;
/*     */         } 
/*     */       } else {
/* 235 */         object = ((Hashtable)this.table).remove(paramObject);
/*     */       } 
/* 237 */       if (size() == 7 && !isArray()) {
/* 238 */         shrink();
/*     */       }
/*     */     } 
/* 241 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 248 */     this.table = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 255 */     ArrayTable arrayTable = new ArrayTable();
/* 256 */     if (isArray()) {
/* 257 */       Object[] arrayOfObject = (Object[])this.table;
/* 258 */       for (byte b = 0; b < arrayOfObject.length - 1; b += 2) {
/* 259 */         arrayTable.put(arrayOfObject[b], arrayOfObject[b + 1]);
/*     */       }
/*     */     } else {
/* 262 */       Hashtable hashtable = (Hashtable)this.table;
/* 263 */       Enumeration<Object> enumeration = hashtable.keys();
/* 264 */       while (enumeration.hasMoreElements()) {
/* 265 */         Object object = enumeration.nextElement();
/* 266 */         arrayTable.put(object, hashtable.get(object));
/*     */       } 
/*     */     } 
/* 269 */     return arrayTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getKeys(Object[] paramArrayOfObject) {
/* 279 */     if (this.table == null) {
/* 280 */       return null;
/*     */     }
/* 282 */     if (isArray()) {
/* 283 */       Object[] arrayOfObject = (Object[])this.table;
/* 284 */       if (paramArrayOfObject == null) {
/* 285 */         paramArrayOfObject = new Object[arrayOfObject.length / 2];
/*     */       }
/* 287 */       for (byte b1 = 0, b2 = 0; b1 < arrayOfObject.length - 1; b1 += 2, 
/* 288 */         b2++) {
/* 289 */         paramArrayOfObject[b2] = arrayOfObject[b1];
/*     */       }
/*     */     } else {
/* 292 */       Hashtable hashtable = (Hashtable)this.table;
/* 293 */       Enumeration enumeration = hashtable.keys();
/* 294 */       int i = hashtable.size();
/* 295 */       if (paramArrayOfObject == null) {
/* 296 */         paramArrayOfObject = new Object[i];
/*     */       }
/* 298 */       while (i > 0) {
/* 299 */         paramArrayOfObject[--i] = enumeration.nextElement();
/*     */       }
/*     */     } 
/* 302 */     return paramArrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isArray() {
/* 310 */     return this.table instanceof Object[];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow() {
/* 317 */     Object[] arrayOfObject = (Object[])this.table;
/* 318 */     Hashtable<Object, Object> hashtable = new Hashtable<>(arrayOfObject.length / 2);
/* 319 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/* 320 */       hashtable.put(arrayOfObject[b], arrayOfObject[b + 1]);
/*     */     }
/* 322 */     this.table = hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void shrink() {
/* 329 */     Hashtable hashtable = (Hashtable)this.table;
/* 330 */     Object[] arrayOfObject = new Object[hashtable.size() * 2];
/* 331 */     Enumeration<Object> enumeration = hashtable.keys();
/* 332 */     byte b = 0;
/*     */     
/* 334 */     while (enumeration.hasMoreElements()) {
/* 335 */       Object object = enumeration.nextElement();
/* 336 */       arrayOfObject[b] = object;
/* 337 */       arrayOfObject[b + 1] = hashtable.get(object);
/* 338 */       b += 2;
/*     */     } 
/* 340 */     this.table = arrayOfObject;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ArrayTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */