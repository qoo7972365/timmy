/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImmutableDescriptor
/*     */   implements Descriptor
/*     */ {
/*     */   private static final long serialVersionUID = 8853308591080540165L;
/*     */   private final String[] names;
/*     */   private final Object[] values;
/*  57 */   private transient int hashCode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final ImmutableDescriptor EMPTY_DESCRIPTOR = new ImmutableDescriptor(new String[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableDescriptor(String[] paramArrayOfString, Object[] paramArrayOfObject) {
/*  74 */     this(makeMap(paramArrayOfString, paramArrayOfObject));
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
/*     */   public ImmutableDescriptor(String... paramVarArgs) {
/*  90 */     this(makeMap(paramVarArgs));
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
/*     */   public ImmutableDescriptor(Map<String, ?> paramMap) {
/* 103 */     if (paramMap == null)
/* 104 */       throw new IllegalArgumentException("Null Map"); 
/* 105 */     TreeMap<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
/*     */     
/* 107 */     for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
/* 108 */       String str = (String)entry.getKey();
/* 109 */       if (str == null || str.equals(""))
/* 110 */         throw new IllegalArgumentException("Empty or null field name"); 
/* 111 */       if (treeMap.containsKey(str))
/* 112 */         throw new IllegalArgumentException("Duplicate name: " + str); 
/* 113 */       treeMap.put(str, entry.getValue());
/*     */     } 
/* 115 */     int i = treeMap.size();
/* 116 */     this.names = (String[])treeMap.keySet().toArray((Object[])new String[i]);
/* 117 */     this.values = treeMap.values().toArray(new Object[i]);
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
/*     */   private Object readResolve() throws InvalidObjectException {
/* 132 */     boolean bool = false;
/* 133 */     if (this.names == null || this.values == null || this.names.length != this.values.length)
/* 134 */       bool = true; 
/* 135 */     if (!bool) {
/* 136 */       if (this.names.length == 0 && getClass() == ImmutableDescriptor.class)
/* 137 */         return EMPTY_DESCRIPTOR; 
/* 138 */       Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
/* 139 */       String str = "";
/* 140 */       for (byte b = 0; b < this.names.length; b++) {
/* 141 */         if (this.names[b] == null || comparator
/* 142 */           .compare(str, this.names[b]) >= 0) {
/* 143 */           bool = true;
/*     */           break;
/*     */         } 
/* 146 */         str = this.names[b];
/*     */       } 
/*     */     } 
/* 149 */     if (bool) {
/* 150 */       throw new InvalidObjectException("Bad names or values");
/*     */     }
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static SortedMap<String, ?> makeMap(String[] paramArrayOfString, Object[] paramArrayOfObject) {
/* 157 */     if (paramArrayOfString == null || paramArrayOfObject == null)
/* 158 */       throw new IllegalArgumentException("Null array parameter"); 
/* 159 */     if (paramArrayOfString.length != paramArrayOfObject.length)
/* 160 */       throw new IllegalArgumentException("Different size arrays"); 
/* 161 */     TreeMap<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
/*     */     
/* 163 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 164 */       String str = paramArrayOfString[b];
/* 165 */       if (str == null || str.equals(""))
/* 166 */         throw new IllegalArgumentException("Empty or null field name"); 
/* 167 */       Object object = treeMap.put(str, paramArrayOfObject[b]);
/* 168 */       if (object != null) {
/* 169 */         throw new IllegalArgumentException("Duplicate field name: " + str);
/*     */       }
/*     */     } 
/*     */     
/* 173 */     return treeMap;
/*     */   }
/*     */   
/*     */   private static SortedMap<String, ?> makeMap(String[] paramArrayOfString) {
/* 177 */     if (paramArrayOfString == null)
/* 178 */       throw new IllegalArgumentException("Null fields parameter"); 
/* 179 */     String[] arrayOfString1 = new String[paramArrayOfString.length];
/* 180 */     String[] arrayOfString2 = new String[paramArrayOfString.length];
/* 181 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 182 */       String str = paramArrayOfString[b];
/* 183 */       int i = str.indexOf('=');
/* 184 */       if (i < 0) {
/* 185 */         throw new IllegalArgumentException("Missing = character: " + str);
/*     */       }
/*     */       
/* 188 */       arrayOfString1[b] = str.substring(0, i);
/*     */       
/* 190 */       arrayOfString2[b] = str.substring(i + 1);
/*     */     } 
/* 192 */     return makeMap(arrayOfString1, (Object[])arrayOfString2);
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
/*     */ 
/*     */   
/*     */   public static ImmutableDescriptor union(Descriptor... paramVarArgs) {
/* 229 */     int i = findNonEmpty(paramVarArgs, 0);
/* 230 */     if (i < 0)
/* 231 */       return EMPTY_DESCRIPTOR; 
/* 232 */     if (paramVarArgs[i] instanceof ImmutableDescriptor && 
/* 233 */       findNonEmpty(paramVarArgs, i + 1) < 0) {
/* 234 */       return (ImmutableDescriptor)paramVarArgs[i];
/*     */     }
/* 236 */     TreeMap<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
/*     */     
/* 238 */     ImmutableDescriptor immutableDescriptor = EMPTY_DESCRIPTOR;
/* 239 */     for (Descriptor descriptor : paramVarArgs) {
/* 240 */       if (descriptor != null) {
/*     */         String[] arrayOfString;
/* 242 */         if (descriptor instanceof ImmutableDescriptor) {
/* 243 */           ImmutableDescriptor immutableDescriptor1 = (ImmutableDescriptor)descriptor;
/* 244 */           arrayOfString = immutableDescriptor1.names;
/* 245 */           if (immutableDescriptor1.getClass() == ImmutableDescriptor.class && arrayOfString.length > immutableDescriptor.names.length)
/*     */           {
/* 247 */             immutableDescriptor = immutableDescriptor1; } 
/*     */         } else {
/* 249 */           arrayOfString = descriptor.getFieldNames();
/* 250 */         }  for (String str : arrayOfString) {
/* 251 */           Object object1 = descriptor.getFieldValue(str);
/* 252 */           Object object2 = treeMap.put(str, object1);
/* 253 */           if (object2 != null) {
/*     */             boolean bool;
/* 255 */             if (object2.getClass().isArray()) {
/* 256 */               bool = Arrays.deepEquals(new Object[] { object2 }, new Object[] { object1 });
/*     */             } else {
/*     */               
/* 259 */               bool = object2.equals(object1);
/* 260 */             }  if (!bool) {
/* 261 */               String str1 = "Inconsistent values for descriptor field " + str + ": " + object2 + " :: " + object1;
/*     */ 
/*     */               
/* 264 */               throw new IllegalArgumentException(str1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 270 */     if (immutableDescriptor.names.length == treeMap.size())
/* 271 */       return immutableDescriptor; 
/* 272 */     return new ImmutableDescriptor(treeMap);
/*     */   }
/*     */   
/*     */   private static boolean isEmpty(Descriptor paramDescriptor) {
/* 276 */     if (paramDescriptor == null)
/* 277 */       return true; 
/* 278 */     if (paramDescriptor instanceof ImmutableDescriptor) {
/* 279 */       return (((ImmutableDescriptor)paramDescriptor).names.length == 0);
/*     */     }
/* 281 */     return ((paramDescriptor.getFieldNames()).length == 0);
/*     */   }
/*     */   
/*     */   private static int findNonEmpty(Descriptor[] paramArrayOfDescriptor, int paramInt) {
/* 285 */     for (int i = paramInt; i < paramArrayOfDescriptor.length; i++) {
/* 286 */       if (!isEmpty(paramArrayOfDescriptor[i]))
/* 287 */         return i; 
/*     */     } 
/* 289 */     return -1;
/*     */   }
/*     */   
/*     */   private int fieldIndex(String paramString) {
/* 293 */     return Arrays.binarySearch(this.names, paramString, String.CASE_INSENSITIVE_ORDER);
/*     */   }
/*     */   
/*     */   public final Object getFieldValue(String paramString) {
/* 297 */     checkIllegalFieldName(paramString);
/* 298 */     int i = fieldIndex(paramString);
/* 299 */     if (i < 0)
/* 300 */       return null; 
/* 301 */     Object object1 = this.values[i];
/* 302 */     if (object1 == null || !object1.getClass().isArray())
/* 303 */       return object1; 
/* 304 */     if (object1 instanceof Object[]) {
/* 305 */       return ((Object[])object1).clone();
/*     */     }
/* 307 */     int j = Array.getLength(object1);
/* 308 */     Object object2 = Array.newInstance(object1.getClass().getComponentType(), j);
/* 309 */     System.arraycopy(object1, 0, object2, 0, j);
/* 310 */     return object2;
/*     */   }
/*     */   
/*     */   public final String[] getFields() {
/* 314 */     String[] arrayOfString = new String[this.names.length];
/* 315 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 316 */       Object object = this.values[b];
/* 317 */       if (object == null) {
/* 318 */         object = "";
/* 319 */       } else if (!(object instanceof String)) {
/* 320 */         object = "(" + object + ")";
/* 321 */       }  arrayOfString[b] = this.names[b] + "=" + object;
/*     */     } 
/* 323 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public final Object[] getFieldValues(String... paramVarArgs) {
/* 327 */     if (paramVarArgs == null)
/* 328 */       return (Object[])this.values.clone(); 
/* 329 */     Object[] arrayOfObject = new Object[paramVarArgs.length];
/* 330 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 331 */       String str = paramVarArgs[b];
/* 332 */       if (str != null && !str.equals(""))
/* 333 */         arrayOfObject[b] = getFieldValue(str); 
/*     */     } 
/* 335 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   public final String[] getFieldNames() {
/* 339 */     return (String[])this.names.clone();
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
/*     */   public boolean equals(Object paramObject) {
/*     */     String[] arrayOfString;
/*     */     Object[] arrayOfObject;
/* 368 */     if (paramObject == this)
/* 369 */       return true; 
/* 370 */     if (!(paramObject instanceof Descriptor)) {
/* 371 */       return false;
/*     */     }
/* 373 */     if (paramObject instanceof ImmutableDescriptor) {
/* 374 */       arrayOfString = ((ImmutableDescriptor)paramObject).names;
/*     */     } else {
/* 376 */       arrayOfString = ((Descriptor)paramObject).getFieldNames();
/* 377 */       Arrays.sort(arrayOfString, String.CASE_INSENSITIVE_ORDER);
/*     */     } 
/* 379 */     if (this.names.length != arrayOfString.length)
/* 380 */       return false; 
/* 381 */     for (byte b = 0; b < this.names.length; b++) {
/* 382 */       if (!this.names[b].equalsIgnoreCase(arrayOfString[b])) {
/* 383 */         return false;
/*     */       }
/*     */     } 
/* 386 */     if (paramObject instanceof ImmutableDescriptor) {
/* 387 */       arrayOfObject = ((ImmutableDescriptor)paramObject).values;
/*     */     } else {
/* 389 */       arrayOfObject = ((Descriptor)paramObject).getFieldValues(arrayOfString);
/* 390 */     }  return Arrays.deepEquals(this.values, arrayOfObject);
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
/*     */   public int hashCode() {
/* 417 */     if (this.hashCode == -1) {
/* 418 */       this.hashCode = Util.hashCode(this.names, this.values);
/*     */     }
/* 420 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 425 */     StringBuilder stringBuilder = new StringBuilder("{");
/* 426 */     for (byte b = 0; b < this.names.length; b++) {
/* 427 */       if (b > 0)
/* 428 */         stringBuilder.append(", "); 
/* 429 */       stringBuilder.append(this.names[b]).append("=");
/* 430 */       Object object = this.values[b];
/* 431 */       if (object != null && object.getClass().isArray()) {
/* 432 */         String str = Arrays.deepToString(new Object[] { object });
/* 433 */         str = str.substring(1, str.length() - 1);
/* 434 */         object = str;
/*     */       } 
/* 436 */       stringBuilder.append(String.valueOf(object));
/*     */     } 
/* 438 */     return stringBuilder.append("}").toString();
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
/*     */   public boolean isValid() {
/* 453 */     return true;
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
/*     */   public Descriptor clone() {
/* 472 */     return this;
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
/*     */   public final void setFields(String[] paramArrayOfString, Object[] paramArrayOfObject) throws RuntimeOperationsException {
/* 486 */     if (paramArrayOfString == null || paramArrayOfObject == null)
/* 487 */       illegal("Null argument"); 
/* 488 */     if (paramArrayOfString.length != paramArrayOfObject.length)
/* 489 */       illegal("Different array sizes");  byte b;
/* 490 */     for (b = 0; b < paramArrayOfString.length; b++)
/* 491 */       checkIllegalFieldName(paramArrayOfString[b]); 
/* 492 */     for (b = 0; b < paramArrayOfString.length; b++) {
/* 493 */       setField(paramArrayOfString[b], paramArrayOfObject[b]);
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
/*     */   public final void setField(String paramString, Object paramObject) throws RuntimeOperationsException {
/* 507 */     checkIllegalFieldName(paramString);
/* 508 */     int i = fieldIndex(paramString);
/* 509 */     if (i < 0)
/* 510 */       unsupported(); 
/* 511 */     Object object = this.values[i];
/* 512 */     if ((object == null) ? (paramObject != null) : 
/*     */       
/* 514 */       !object.equals(paramObject)) {
/* 515 */       unsupported();
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
/*     */   public final void removeField(String paramString) {
/* 530 */     if (paramString != null && fieldIndex(paramString) >= 0)
/* 531 */       unsupported(); 
/*     */   }
/*     */   
/*     */   static Descriptor nonNullDescriptor(Descriptor paramDescriptor) {
/* 535 */     if (paramDescriptor == null) {
/* 536 */       return EMPTY_DESCRIPTOR;
/*     */     }
/* 538 */     return paramDescriptor;
/*     */   }
/*     */   
/*     */   private static void checkIllegalFieldName(String paramString) {
/* 542 */     if (paramString == null || paramString.equals(""))
/* 543 */       illegal("Null or empty field name"); 
/*     */   }
/*     */   
/*     */   private static void unsupported() {
/* 547 */     UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Descriptor is read-only");
/*     */     
/* 549 */     throw new RuntimeOperationsException(unsupportedOperationException);
/*     */   }
/*     */   
/*     */   private static void illegal(String paramString) {
/* 553 */     IllegalArgumentException illegalArgumentException = new IllegalArgumentException(paramString);
/* 554 */     throw new RuntimeOperationsException(illegalArgumentException);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/ImmutableDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */