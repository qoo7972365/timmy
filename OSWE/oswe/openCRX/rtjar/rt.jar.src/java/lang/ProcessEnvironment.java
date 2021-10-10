/*     */ package java.lang;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProcessEnvironment
/*     */ {
/*     */   private static final HashMap<Variable, Value> theEnvironment;
/*     */   
/*     */   static {
/*  70 */     byte[][] arrayOfByte = environ();
/*  71 */     theEnvironment = new HashMap<>(arrayOfByte.length / 2 + 3);
/*     */ 
/*     */     
/*  74 */     for (int i = arrayOfByte.length - 1; i > 0; i -= 2) {
/*  75 */       theEnvironment.put(Variable.valueOf(arrayOfByte[i - 1]), 
/*  76 */           Value.valueOf(arrayOfByte[i]));
/*     */     }
/*     */   }
/*     */   
/*  80 */   private static final Map<String, String> theUnmodifiableEnvironment = Collections.unmodifiableMap(new StringEnvironment(theEnvironment));
/*     */   
/*     */   static final int MIN_NAME_LENGTH = 0;
/*     */   
/*     */   static String getenv(String paramString) {
/*  85 */     return theUnmodifiableEnvironment.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<String, String> getenv() {
/*  90 */     return theUnmodifiableEnvironment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Map<String, String> environment() {
/*  96 */     return new StringEnvironment((Map<Variable, Value>)theEnvironment
/*  97 */         .clone());
/*     */   }
/*     */ 
/*     */   
/*     */   static Map<String, String> emptyEnvironment(int paramInt) {
/* 102 */     return new StringEnvironment(new HashMap<>(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateVariable(String paramString) {
/* 112 */     if (paramString.indexOf('=') != -1 || paramString
/* 113 */       .indexOf(false) != -1) {
/* 114 */       throw new IllegalArgumentException("Invalid environment variable name: \"" + paramString + "\"");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateValue(String paramString) {
/* 120 */     if (paramString.indexOf(false) != -1) {
/* 121 */       throw new IllegalArgumentException("Invalid environment variable value: \"" + paramString + "\"");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class ExternalData
/*     */   {
/*     */     protected final String str;
/*     */     protected final byte[] bytes;
/*     */     
/*     */     protected ExternalData(String param1String, byte[] param1ArrayOfbyte) {
/* 132 */       this.str = param1String;
/* 133 */       this.bytes = param1ArrayOfbyte;
/*     */     }
/*     */     
/*     */     public byte[] getBytes() {
/* 137 */       return this.bytes;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 141 */       return this.str;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 145 */       return (param1Object instanceof ExternalData && ProcessEnvironment
/* 146 */         .arrayEquals(getBytes(), ((ExternalData)param1Object).getBytes()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 150 */       return ProcessEnvironment.arrayHash(getBytes());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Variable
/*     */     extends ExternalData
/*     */     implements Comparable<Variable> {
/*     */     protected Variable(String param1String, byte[] param1ArrayOfbyte) {
/* 158 */       super(param1String, param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public static Variable valueOfQueryOnly(Object param1Object) {
/* 162 */       return valueOfQueryOnly((String)param1Object);
/*     */     }
/*     */     
/*     */     public static Variable valueOfQueryOnly(String param1String) {
/* 166 */       return new Variable(param1String, param1String.getBytes());
/*     */     }
/*     */     
/*     */     public static Variable valueOf(String param1String) {
/* 170 */       ProcessEnvironment.validateVariable(param1String);
/* 171 */       return valueOfQueryOnly(param1String);
/*     */     }
/*     */     
/*     */     public static Variable valueOf(byte[] param1ArrayOfbyte) {
/* 175 */       return new Variable(new String(param1ArrayOfbyte), param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public int compareTo(Variable param1Variable) {
/* 179 */       return ProcessEnvironment.arrayCompare(getBytes(), param1Variable.getBytes());
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 183 */       return (param1Object instanceof Variable && super.equals(param1Object));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Value
/*     */     extends ExternalData
/*     */     implements Comparable<Value> {
/*     */     protected Value(String param1String, byte[] param1ArrayOfbyte) {
/* 191 */       super(param1String, param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public static Value valueOfQueryOnly(Object param1Object) {
/* 195 */       return valueOfQueryOnly((String)param1Object);
/*     */     }
/*     */     
/*     */     public static Value valueOfQueryOnly(String param1String) {
/* 199 */       return new Value(param1String, param1String.getBytes());
/*     */     }
/*     */     
/*     */     public static Value valueOf(String param1String) {
/* 203 */       ProcessEnvironment.validateValue(param1String);
/* 204 */       return valueOfQueryOnly(param1String);
/*     */     }
/*     */     
/*     */     public static Value valueOf(byte[] param1ArrayOfbyte) {
/* 208 */       return new Value(new String(param1ArrayOfbyte), param1ArrayOfbyte);
/*     */     }
/*     */     
/*     */     public int compareTo(Value param1Value) {
/* 212 */       return ProcessEnvironment.arrayCompare(getBytes(), param1Value.getBytes());
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 216 */       return (param1Object instanceof Value && super.equals(param1Object));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StringEnvironment
/*     */     extends AbstractMap<String, String>
/*     */   {
/*     */     private Map<ProcessEnvironment.Variable, ProcessEnvironment.Value> m;
/*     */     
/*     */     private static String toString(ProcessEnvironment.Value param1Value) {
/* 226 */       return (param1Value == null) ? null : param1Value.toString();
/*     */     }
/* 228 */     public StringEnvironment(Map<ProcessEnvironment.Variable, ProcessEnvironment.Value> param1Map) { this.m = param1Map; }
/* 229 */     public int size() { return this.m.size(); }
/* 230 */     public boolean isEmpty() { return this.m.isEmpty(); } public void clear() {
/* 231 */       this.m.clear();
/*     */     } public boolean containsKey(Object param1Object) {
/* 233 */       return this.m.containsKey(ProcessEnvironment.Variable.valueOfQueryOnly(param1Object));
/*     */     }
/*     */     public boolean containsValue(Object param1Object) {
/* 236 */       return this.m.containsValue(ProcessEnvironment.Value.valueOfQueryOnly(param1Object));
/*     */     }
/*     */     public String get(Object param1Object) {
/* 239 */       return toString(this.m.get(ProcessEnvironment.Variable.valueOfQueryOnly(param1Object)));
/*     */     }
/*     */     public String put(String param1String1, String param1String2) {
/* 242 */       return toString(this.m.put(ProcessEnvironment.Variable.valueOf(param1String1), 
/* 243 */             ProcessEnvironment.Value.valueOf(param1String2)));
/*     */     }
/*     */     public String remove(Object param1Object) {
/* 246 */       return toString(this.m.remove(ProcessEnvironment.Variable.valueOfQueryOnly(param1Object)));
/*     */     }
/*     */     public Set<String> keySet() {
/* 249 */       return new ProcessEnvironment.StringKeySet(this.m.keySet());
/*     */     }
/*     */     public Set<Map.Entry<String, String>> entrySet() {
/* 252 */       return new ProcessEnvironment.StringEntrySet(this.m.entrySet());
/*     */     }
/*     */     public Collection<String> values() {
/* 255 */       return new ProcessEnvironment.StringValues(this.m.values());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] toEnvironmentBlock(int[] param1ArrayOfint) {
/* 271 */       int i = this.m.size() * 2;
/* 272 */       for (Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> entry : this.m.entrySet()) {
/* 273 */         i += (((ProcessEnvironment.Variable)entry.getKey()).getBytes()).length;
/* 274 */         i += (((ProcessEnvironment.Value)entry.getValue()).getBytes()).length;
/*     */       } 
/*     */       
/* 277 */       byte[] arrayOfByte = new byte[i];
/*     */       
/* 279 */       int j = 0;
/* 280 */       for (Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> entry : this.m.entrySet()) {
/* 281 */         byte[] arrayOfByte1 = ((ProcessEnvironment.Variable)entry.getKey()).getBytes();
/* 282 */         byte[] arrayOfByte2 = ((ProcessEnvironment.Value)entry.getValue()).getBytes();
/* 283 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte, j, arrayOfByte1.length);
/* 284 */         j += arrayOfByte1.length;
/* 285 */         arrayOfByte[j++] = 61;
/* 286 */         System.arraycopy(arrayOfByte2, 0, arrayOfByte, j, arrayOfByte2.length);
/* 287 */         j += arrayOfByte2.length + 1;
/*     */       } 
/*     */ 
/*     */       
/* 291 */       param1ArrayOfint[0] = this.m.size();
/* 292 */       return arrayOfByte;
/*     */     }
/*     */   }
/*     */   
/*     */   static byte[] toEnvironmentBlock(Map<String, String> paramMap, int[] paramArrayOfint) {
/* 297 */     return (paramMap == null) ? null : ((StringEnvironment)paramMap)
/* 298 */       .toEnvironmentBlock(paramArrayOfint);
/*     */   }
/*     */   
/*     */   private static class StringEntry
/*     */     implements Map.Entry<String, String>
/*     */   {
/*     */     private final Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> e;
/*     */     
/* 306 */     public StringEntry(Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> param1Entry) { this.e = param1Entry; }
/* 307 */     public String getKey() { return ((ProcessEnvironment.Variable)this.e.getKey()).toString(); } public String getValue() {
/* 308 */       return ((ProcessEnvironment.Value)this.e.getValue()).toString();
/*     */     } public String setValue(String param1String) {
/* 310 */       return ((ProcessEnvironment.Value)this.e.setValue(ProcessEnvironment.Value.valueOf(param1String))).toString();
/*     */     } public String toString() {
/* 312 */       return getKey() + "=" + getValue();
/*     */     } public boolean equals(Object param1Object) {
/* 314 */       return (param1Object instanceof StringEntry && this.e
/* 315 */         .equals(((StringEntry)param1Object).e));
/*     */     } public int hashCode() {
/* 317 */       return this.e.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StringEntrySet extends AbstractSet<Map.Entry<String, String>> {
/*     */     private final Set<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> s;
/*     */     
/* 324 */     public StringEntrySet(Set<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> param1Set) { this.s = param1Set; }
/* 325 */     public int size() { return this.s.size(); }
/* 326 */     public boolean isEmpty() { return this.s.isEmpty(); } public void clear() {
/* 327 */       this.s.clear();
/*     */     } public Iterator<Map.Entry<String, String>> iterator() {
/* 329 */       return new Iterator<Map.Entry<String, String>>() {
/* 330 */           Iterator<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> i = ProcessEnvironment.StringEntrySet.this.s.iterator(); public boolean hasNext() {
/* 331 */             return this.i.hasNext();
/*     */           } public Map.Entry<String, String> next() {
/* 333 */             return new ProcessEnvironment.StringEntry(this.i.next());
/*     */           } public void remove() {
/* 335 */             this.i.remove();
/*     */           }
/*     */         };
/*     */     } private static Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> vvEntry(final Object o) {
/* 339 */       if (o instanceof ProcessEnvironment.StringEntry)
/* 340 */         return ((ProcessEnvironment.StringEntry)o).e; 
/* 341 */       return new Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>() {
/*     */           public ProcessEnvironment.Variable getKey() {
/* 343 */             return ProcessEnvironment.Variable.valueOfQueryOnly(((Map.Entry)o).getKey());
/*     */           }
/*     */           public ProcessEnvironment.Value getValue() {
/* 346 */             return ProcessEnvironment.Value.valueOfQueryOnly(((Map.Entry)o).getValue());
/*     */           }
/*     */           public ProcessEnvironment.Value setValue(ProcessEnvironment.Value param2Value) {
/* 349 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/* 353 */     public boolean contains(Object param1Object) { return this.s.contains(vvEntry(param1Object)); } public boolean remove(Object param1Object) {
/* 354 */       return this.s.remove(vvEntry(param1Object));
/*     */     } public boolean equals(Object param1Object) {
/* 356 */       return (param1Object instanceof StringEntrySet && this.s
/* 357 */         .equals(((StringEntrySet)param1Object).s));
/*     */     } public int hashCode() {
/* 359 */       return this.s.hashCode();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StringValues extends AbstractCollection<String> {
/*     */     private final Collection<ProcessEnvironment.Value> c;
/*     */     
/* 366 */     public StringValues(Collection<ProcessEnvironment.Value> param1Collection) { this.c = param1Collection; }
/* 367 */     public int size() { return this.c.size(); }
/* 368 */     public boolean isEmpty() { return this.c.isEmpty(); } public void clear() {
/* 369 */       this.c.clear();
/*     */     } public Iterator<String> iterator() {
/* 371 */       return new Iterator<String>() {
/* 372 */           Iterator<ProcessEnvironment.Value> i = ProcessEnvironment.StringValues.this.c.iterator();
/* 373 */           public boolean hasNext() { return this.i.hasNext(); }
/* 374 */           public String next() { return ((ProcessEnvironment.Value)this.i.next()).toString(); } public void remove() {
/* 375 */             this.i.remove();
/*     */           }
/*     */         };
/*     */     } public boolean contains(Object param1Object) {
/* 379 */       return this.c.contains(ProcessEnvironment.Value.valueOfQueryOnly(param1Object));
/*     */     }
/*     */     public boolean remove(Object param1Object) {
/* 382 */       return this.c.remove(ProcessEnvironment.Value.valueOfQueryOnly(param1Object));
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 385 */       return (param1Object instanceof StringValues && this.c
/* 386 */         .equals(((StringValues)param1Object).c));
/*     */     } public int hashCode() {
/* 388 */       return this.c.hashCode();
/*     */     }
/*     */   }
/*     */   private static class StringKeySet extends AbstractSet<String> { private final Set<ProcessEnvironment.Variable> s;
/*     */     
/* 393 */     public StringKeySet(Set<ProcessEnvironment.Variable> param1Set) { this.s = param1Set; }
/* 394 */     public int size() { return this.s.size(); }
/* 395 */     public boolean isEmpty() { return this.s.isEmpty(); } public void clear() {
/* 396 */       this.s.clear();
/*     */     } public Iterator<String> iterator() {
/* 398 */       return new Iterator<String>() {
/* 399 */           Iterator<ProcessEnvironment.Variable> i = ProcessEnvironment.StringKeySet.this.s.iterator();
/* 400 */           public boolean hasNext() { return this.i.hasNext(); }
/* 401 */           public String next() { return ((ProcessEnvironment.Variable)this.i.next()).toString(); } public void remove() {
/* 402 */             this.i.remove();
/*     */           }
/*     */         };
/*     */     } public boolean contains(Object param1Object) {
/* 406 */       return this.s.contains(ProcessEnvironment.Variable.valueOfQueryOnly(param1Object));
/*     */     }
/*     */     public boolean remove(Object param1Object) {
/* 409 */       return this.s.remove(ProcessEnvironment.Variable.valueOfQueryOnly(param1Object));
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int arrayCompare(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 415 */     int i = (paramArrayOfbyte1.length < paramArrayOfbyte2.length) ? paramArrayOfbyte1.length : paramArrayOfbyte2.length;
/* 416 */     for (byte b = 0; b < i; b++) {
/* 417 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 418 */         return paramArrayOfbyte1[b] - paramArrayOfbyte2[b]; 
/* 419 */     }  return paramArrayOfbyte1.length - paramArrayOfbyte2.length;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean arrayEquals(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 424 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length)
/* 425 */       return false; 
/* 426 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 427 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 428 */         return false; 
/* 429 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int arrayHash(byte[] paramArrayOfbyte) {
/* 434 */     int i = 0;
/* 435 */     for (byte b = 0; b < paramArrayOfbyte.length; b++)
/* 436 */       i = 31 * i + paramArrayOfbyte[b]; 
/* 437 */     return i;
/*     */   }
/*     */   
/*     */   private static native byte[][] environ();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ProcessEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */