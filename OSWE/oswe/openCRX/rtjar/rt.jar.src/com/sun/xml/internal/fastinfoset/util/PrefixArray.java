/*     */ package com.sun.xml.internal.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefixArray
/*     */   extends ValueArray
/*     */ {
/*     */   public static final int PREFIX_MAP_SIZE = 64;
/*     */   private int _initialCapacity;
/*     */   public String[] _array;
/*     */   private PrefixArray _readOnlyArray;
/*     */   
/*     */   private static class PrefixEntry
/*     */   {
/*     */     private PrefixEntry next;
/*     */     private int prefixId;
/*     */     
/*     */     private PrefixEntry() {}
/*     */   }
/*  50 */   private PrefixEntry[] _prefixMap = new PrefixEntry[64];
/*     */   
/*     */   private PrefixEntry _prefixPool;
/*     */   
/*     */   private NamespaceEntry _namespacePool;
/*     */   
/*     */   private NamespaceEntry[] _inScopeNamespaces;
/*     */   public int[] _currentInScope;
/*     */   public int _declarationId;
/*     */   
/*     */   private static class NamespaceEntry
/*     */   {
/*     */     private NamespaceEntry next;
/*     */     private int declarationId;
/*     */     private int namespaceIndex;
/*     */     private String prefix;
/*     */     private String namespaceName;
/*     */     private int prefixEntryIndex;
/*     */     
/*     */     private NamespaceEntry() {}
/*     */   }
/*     */   
/*     */   public PrefixArray(int initialCapacity, int maximumCapacity) {
/*  73 */     this._initialCapacity = initialCapacity;
/*  74 */     this._maximumCapacity = maximumCapacity;
/*     */     
/*  76 */     this._array = new String[initialCapacity];
/*     */ 
/*     */ 
/*     */     
/*  80 */     this._inScopeNamespaces = new NamespaceEntry[initialCapacity + 2];
/*  81 */     this._currentInScope = new int[initialCapacity + 2];
/*     */     
/*  83 */     increaseNamespacePool(initialCapacity);
/*  84 */     increasePrefixPool(initialCapacity);
/*     */     
/*  86 */     initializeEntries();
/*     */   }
/*     */   
/*     */   public PrefixArray() {
/*  90 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   private final void initializeEntries() {
/*  94 */     this._inScopeNamespaces[0] = this._namespacePool;
/*  95 */     this._namespacePool = this._namespacePool.next;
/*  96 */     (this._inScopeNamespaces[0]).next = null;
/*  97 */     (this._inScopeNamespaces[0]).prefix = "";
/*  98 */     (this._inScopeNamespaces[0]).namespaceName = "";
/*  99 */     (this._inScopeNamespaces[0]).namespaceIndex = this._currentInScope[0] = 0;
/*     */     
/* 101 */     int index = KeyIntMap.indexFor(KeyIntMap.hashHash((this._inScopeNamespaces[0]).prefix.hashCode()), this._prefixMap.length);
/* 102 */     this._prefixMap[index] = this._prefixPool;
/* 103 */     this._prefixPool = this._prefixPool.next;
/* 104 */     (this._prefixMap[index]).next = null;
/* 105 */     (this._prefixMap[index]).prefixId = 0;
/*     */ 
/*     */     
/* 108 */     this._inScopeNamespaces[1] = this._namespacePool;
/* 109 */     this._namespacePool = this._namespacePool.next;
/* 110 */     (this._inScopeNamespaces[1]).next = null;
/* 111 */     (this._inScopeNamespaces[1]).prefix = "xml";
/* 112 */     (this._inScopeNamespaces[1]).namespaceName = "http://www.w3.org/XML/1998/namespace";
/* 113 */     (this._inScopeNamespaces[1]).namespaceIndex = this._currentInScope[1] = 1;
/*     */     
/* 115 */     index = KeyIntMap.indexFor(KeyIntMap.hashHash((this._inScopeNamespaces[1]).prefix.hashCode()), this._prefixMap.length);
/* 116 */     if (this._prefixMap[index] == null) {
/* 117 */       this._prefixMap[index] = this._prefixPool;
/* 118 */       this._prefixPool = this._prefixPool.next;
/* 119 */       (this._prefixMap[index]).next = null;
/*     */     } else {
/* 121 */       PrefixEntry e = this._prefixMap[index];
/* 122 */       this._prefixMap[index] = this._prefixPool;
/* 123 */       this._prefixPool = this._prefixPool.next;
/* 124 */       (this._prefixMap[index]).next = e;
/*     */     } 
/* 126 */     (this._prefixMap[index]).prefixId = 1;
/*     */   }
/*     */   
/*     */   private final void increaseNamespacePool(int capacity) {
/* 130 */     if (this._namespacePool == null) {
/* 131 */       this._namespacePool = new NamespaceEntry();
/*     */     }
/*     */     
/* 134 */     for (int i = 0; i < capacity; i++) {
/* 135 */       NamespaceEntry ne = new NamespaceEntry();
/* 136 */       ne.next = this._namespacePool;
/* 137 */       this._namespacePool = ne;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void increasePrefixPool(int capacity) {
/* 142 */     if (this._prefixPool == null) {
/* 143 */       this._prefixPool = new PrefixEntry();
/*     */     }
/*     */     
/* 146 */     for (int i = 0; i < capacity; i++) {
/* 147 */       PrefixEntry pe = new PrefixEntry();
/* 148 */       pe.next = this._prefixPool;
/* 149 */       this._prefixPool = pe;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int countNamespacePool() {
/* 154 */     int i = 0;
/* 155 */     NamespaceEntry e = this._namespacePool;
/* 156 */     while (e != null) {
/* 157 */       i++;
/* 158 */       e = e.next;
/*     */     } 
/* 160 */     return i;
/*     */   }
/*     */   
/*     */   public int countPrefixPool() {
/* 164 */     int i = 0;
/* 165 */     PrefixEntry e = this._prefixPool;
/* 166 */     while (e != null) {
/* 167 */       i++;
/* 168 */       e = e.next;
/*     */     } 
/* 170 */     return i;
/*     */   }
/*     */   
/*     */   public final void clear() {
/* 174 */     for (int i = this._readOnlyArraySize; i < this._size; i++) {
/* 175 */       this._array[i] = null;
/*     */     }
/* 177 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */   
/*     */   public final void clearCompletely() {
/* 181 */     this._prefixPool = null;
/* 182 */     this._namespacePool = null;
/*     */     int i;
/* 184 */     for (i = 0; i < this._size + 2; i++) {
/* 185 */       this._currentInScope[i] = 0;
/* 186 */       this._inScopeNamespaces[i] = null;
/*     */     } 
/*     */     
/* 189 */     for (i = 0; i < this._prefixMap.length; i++) {
/* 190 */       this._prefixMap[i] = null;
/*     */     }
/*     */     
/* 193 */     increaseNamespacePool(this._initialCapacity);
/* 194 */     increasePrefixPool(this._initialCapacity);
/*     */     
/* 196 */     initializeEntries();
/*     */     
/* 198 */     this._declarationId = 0;
/*     */     
/* 200 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] getArray() {
/* 208 */     if (this._array == null) return null;
/*     */     
/* 210 */     String[] clonedArray = new String[this._array.length];
/* 211 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/* 212 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/* 216 */     if (!(readOnlyArray instanceof PrefixArray)) {
/* 217 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance()
/* 218 */           .getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/* 221 */     setReadOnlyArray((PrefixArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(PrefixArray readOnlyArray, boolean clear) {
/* 225 */     if (readOnlyArray != null) {
/* 226 */       this._readOnlyArray = readOnlyArray;
/* 227 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/* 229 */       clearCompletely();
/*     */ 
/*     */       
/* 232 */       this._inScopeNamespaces = new NamespaceEntry[this._readOnlyArraySize + this._inScopeNamespaces.length];
/* 233 */       this._currentInScope = new int[this._readOnlyArraySize + this._currentInScope.length];
/*     */       
/* 235 */       initializeEntries();
/*     */       
/* 237 */       if (clear) {
/* 238 */         clear();
/*     */       }
/*     */       
/* 241 */       this._array = getCompleteArray();
/* 242 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String[] getCompleteArray() {
/* 247 */     if (this._readOnlyArray == null)
/*     */     {
/* 249 */       return getArray();
/*     */     }
/*     */     
/* 252 */     String[] ra = this._readOnlyArray.getCompleteArray();
/* 253 */     String[] a = new String[this._readOnlyArraySize + this._array.length];
/* 254 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 255 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String get(int i) {
/* 260 */     return this._array[i];
/*     */   }
/*     */   
/*     */   public final int add(String s) {
/* 264 */     if (this._size == this._array.length) {
/* 265 */       resize();
/*     */     }
/*     */     
/* 268 */     this._array[this._size++] = s;
/* 269 */     return this._size;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 273 */     if (this._size == this._maximumCapacity) {
/* 274 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 277 */     int newSize = this._size * 3 / 2 + 1;
/* 278 */     if (newSize > this._maximumCapacity) {
/* 279 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 282 */     String[] newArray = new String[newSize];
/* 283 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 284 */     this._array = newArray;
/*     */     
/* 286 */     newSize += 2;
/* 287 */     NamespaceEntry[] newInScopeNamespaces = new NamespaceEntry[newSize];
/* 288 */     System.arraycopy(this._inScopeNamespaces, 0, newInScopeNamespaces, 0, this._inScopeNamespaces.length);
/* 289 */     this._inScopeNamespaces = newInScopeNamespaces;
/*     */     
/* 291 */     int[] newCurrentInScope = new int[newSize];
/* 292 */     System.arraycopy(this._currentInScope, 0, newCurrentInScope, 0, this._currentInScope.length);
/* 293 */     this._currentInScope = newCurrentInScope;
/*     */   }
/*     */   
/*     */   public final void clearDeclarationIds() {
/* 297 */     for (int i = 0; i < this._size; i++) {
/* 298 */       NamespaceEntry e = this._inScopeNamespaces[i];
/* 299 */       if (e != null) {
/* 300 */         e.declarationId = 0;
/*     */       }
/*     */     } 
/*     */     
/* 304 */     this._declarationId = 1;
/*     */   }
/*     */   
/*     */   public final void pushScope(int prefixIndex, int namespaceIndex) throws FastInfosetException {
/* 308 */     if (this._namespacePool == null) {
/* 309 */       increaseNamespacePool(16);
/*     */     }
/*     */     
/* 312 */     NamespaceEntry e = this._namespacePool;
/* 313 */     this._namespacePool = e.next;
/*     */     
/* 315 */     NamespaceEntry current = this._inScopeNamespaces[++prefixIndex];
/* 316 */     if (current == null) {
/* 317 */       e.declarationId = this._declarationId;
/* 318 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 319 */       e.next = null;
/*     */       
/* 321 */       this._inScopeNamespaces[prefixIndex] = e;
/* 322 */     } else if (current.declarationId < this._declarationId) {
/* 323 */       e.declarationId = this._declarationId;
/* 324 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 325 */       e.next = current;
/*     */       
/* 327 */       current.declarationId = 0;
/* 328 */       this._inScopeNamespaces[prefixIndex] = e;
/*     */     } else {
/* 330 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.duplicateNamespaceAttribute"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void pushScopeWithPrefixEntry(String prefix, String namespaceName, int prefixIndex, int namespaceIndex) throws FastInfosetException {
/* 336 */     if (this._namespacePool == null) {
/* 337 */       increaseNamespacePool(16);
/*     */     }
/* 339 */     if (this._prefixPool == null) {
/* 340 */       increasePrefixPool(16);
/*     */     }
/*     */     
/* 343 */     NamespaceEntry e = this._namespacePool;
/* 344 */     this._namespacePool = e.next;
/*     */     
/* 346 */     NamespaceEntry current = this._inScopeNamespaces[++prefixIndex];
/* 347 */     if (current == null) {
/* 348 */       e.declarationId = this._declarationId;
/* 349 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 350 */       e.next = null;
/*     */       
/* 352 */       this._inScopeNamespaces[prefixIndex] = e;
/* 353 */     } else if (current.declarationId < this._declarationId) {
/* 354 */       e.declarationId = this._declarationId;
/* 355 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 356 */       e.next = current;
/*     */       
/* 358 */       current.declarationId = 0;
/* 359 */       this._inScopeNamespaces[prefixIndex] = e;
/*     */     } else {
/* 361 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.duplicateNamespaceAttribute"));
/*     */     } 
/*     */     
/* 364 */     PrefixEntry p = this._prefixPool;
/* 365 */     this._prefixPool = this._prefixPool.next;
/* 366 */     p.prefixId = prefixIndex;
/*     */     
/* 368 */     e.prefix = prefix;
/* 369 */     e.namespaceName = namespaceName;
/* 370 */     e.prefixEntryIndex = KeyIntMap.indexFor(KeyIntMap.hashHash(prefix.hashCode()), this._prefixMap.length);
/*     */     
/* 372 */     PrefixEntry pCurrent = this._prefixMap[e.prefixEntryIndex];
/* 373 */     p.next = pCurrent;
/* 374 */     this._prefixMap[e.prefixEntryIndex] = p;
/*     */   }
/*     */   
/*     */   public final void popScope(int prefixIndex) {
/* 378 */     NamespaceEntry e = this._inScopeNamespaces[++prefixIndex];
/* 379 */     this._inScopeNamespaces[prefixIndex] = e.next;
/* 380 */     this._currentInScope[prefixIndex] = (e.next != null) ? e.next.namespaceIndex : 0;
/*     */     
/* 382 */     e.next = this._namespacePool;
/* 383 */     this._namespacePool = e;
/*     */   }
/*     */   
/*     */   public final void popScopeWithPrefixEntry(int prefixIndex) {
/* 387 */     NamespaceEntry e = this._inScopeNamespaces[++prefixIndex];
/*     */     
/* 389 */     this._inScopeNamespaces[prefixIndex] = e.next;
/* 390 */     this._currentInScope[prefixIndex] = (e.next != null) ? e.next.namespaceIndex : 0;
/*     */     
/* 392 */     e.prefix = e.namespaceName = null;
/* 393 */     e.next = this._namespacePool;
/* 394 */     this._namespacePool = e;
/*     */     
/* 396 */     PrefixEntry current = this._prefixMap[e.prefixEntryIndex];
/* 397 */     if (current.prefixId == prefixIndex) {
/* 398 */       this._prefixMap[e.prefixEntryIndex] = current.next;
/* 399 */       current.next = this._prefixPool;
/* 400 */       this._prefixPool = current;
/*     */     } else {
/* 402 */       PrefixEntry prev = current;
/* 403 */       current = current.next;
/* 404 */       while (current != null) {
/* 405 */         if (current.prefixId == prefixIndex) {
/* 406 */           prev.next = current.next;
/* 407 */           current.next = this._prefixPool;
/* 408 */           this._prefixPool = current;
/*     */           break;
/*     */         } 
/* 411 */         prev = current;
/* 412 */         current = current.next;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String getNamespaceFromPrefix(String prefix) {
/* 418 */     int index = KeyIntMap.indexFor(KeyIntMap.hashHash(prefix.hashCode()), this._prefixMap.length);
/* 419 */     PrefixEntry pe = this._prefixMap[index];
/* 420 */     while (pe != null) {
/* 421 */       NamespaceEntry ne = this._inScopeNamespaces[pe.prefixId];
/* 422 */       if (prefix == ne.prefix || prefix.equals(ne.prefix)) {
/* 423 */         return ne.namespaceName;
/*     */       }
/* 425 */       pe = pe.next;
/*     */     } 
/*     */     
/* 428 */     return null;
/*     */   }
/*     */   
/*     */   public final String getPrefixFromNamespace(String namespaceName) {
/* 432 */     int position = 0;
/* 433 */     while (++position < this._size + 2) {
/* 434 */       NamespaceEntry ne = this._inScopeNamespaces[position];
/* 435 */       if (ne != null && namespaceName.equals(ne.namespaceName)) {
/* 436 */         return ne.prefix;
/*     */       }
/*     */     } 
/*     */     
/* 440 */     return null;
/*     */   }
/*     */   
/*     */   public final Iterator getPrefixes() {
/* 444 */     return new Iterator() {
/* 445 */         int _position = 1;
/* 446 */         PrefixArray.NamespaceEntry _ne = PrefixArray.this._inScopeNamespaces[this._position];
/*     */         
/*     */         public boolean hasNext() {
/* 449 */           return (this._ne != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 453 */           if (this._position == PrefixArray.this._size + 2) {
/* 454 */             throw new NoSuchElementException();
/*     */           }
/*     */           
/* 457 */           String prefix = this._ne.prefix;
/* 458 */           moveToNext();
/* 459 */           return prefix;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 463 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         private final void moveToNext() {
/* 467 */           while (++this._position < PrefixArray.this._size + 2) {
/* 468 */             this._ne = PrefixArray.this._inScopeNamespaces[this._position];
/* 469 */             if (this._ne != null) {
/*     */               return;
/*     */             }
/*     */           } 
/* 473 */           this._ne = null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterator getPrefixesFromNamespace(final String namespaceName) {
/* 480 */     return new Iterator()
/*     */       {
/*     */         String _namespaceName;
/*     */         
/*     */         int _position;
/*     */         
/*     */         PrefixArray.NamespaceEntry _ne;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 490 */           return (this._ne != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 494 */           if (this._position == PrefixArray.this._size + 2) {
/* 495 */             throw new NoSuchElementException();
/*     */           }
/*     */           
/* 498 */           String prefix = this._ne.prefix;
/* 499 */           moveToNext();
/* 500 */           return prefix;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 504 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         private final void moveToNext() {
/* 508 */           while (++this._position < PrefixArray.this._size + 2) {
/* 509 */             this._ne = PrefixArray.this._inScopeNamespaces[this._position];
/* 510 */             if (this._ne != null && this._namespaceName.equals(this._ne.namespaceName)) {
/*     */               return;
/*     */             }
/*     */           } 
/* 514 */           this._ne = null;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/util/PrefixArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */