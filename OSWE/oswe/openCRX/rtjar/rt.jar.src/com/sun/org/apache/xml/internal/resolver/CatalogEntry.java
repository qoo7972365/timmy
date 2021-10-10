/*     */ package com.sun.org.apache.xml.internal.resolver;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CatalogEntry
/*     */ {
/*  55 */   protected static AtomicInteger nextEntry = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   protected static final Map<String, Integer> entryTypes = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected static Vector entryArgs = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int addEntryType(String name, int numArgs) {
/*  80 */     int index = nextEntry.getAndIncrement();
/*  81 */     entryTypes.put(name, Integer.valueOf(index));
/*  82 */     entryArgs.add(index, Integer.valueOf(numArgs));
/*     */     
/*  84 */     return index;
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
/*     */   public static int getEntryType(String name) throws CatalogException {
/*  97 */     if (!entryTypes.containsKey(name)) {
/*  98 */       throw new CatalogException(3);
/*     */     }
/*     */     
/* 101 */     Integer iType = entryTypes.get(name);
/*     */     
/* 103 */     if (iType == null) {
/* 104 */       throw new CatalogException(3);
/*     */     }
/*     */     
/* 107 */     return iType.intValue();
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
/*     */   public static int getEntryArgCount(String name) throws CatalogException {
/* 120 */     return getEntryArgCount(getEntryType(name));
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
/*     */   public static int getEntryArgCount(int type) throws CatalogException {
/*     */     try {
/* 133 */       Integer iArgs = entryArgs.get(type);
/* 134 */       return iArgs.intValue();
/* 135 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 136 */       throw new CatalogException(3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 141 */   protected int entryType = 0;
/*     */ 
/*     */   
/* 144 */   protected Vector args = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CatalogEntry() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CatalogEntry(String name, Vector args) throws CatalogException {
/* 163 */     Integer iType = entryTypes.get(name);
/*     */     
/* 165 */     if (iType == null) {
/* 166 */       throw new CatalogException(3);
/*     */     }
/*     */     
/* 169 */     int type = iType.intValue();
/*     */     
/*     */     try {
/* 172 */       Integer iArgs = entryArgs.get(type);
/* 173 */       if (iArgs.intValue() != args.size()) {
/* 174 */         throw new CatalogException(2);
/*     */       }
/* 176 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 177 */       throw new CatalogException(3);
/*     */     } 
/*     */     
/* 180 */     this.entryType = type;
/* 181 */     this.args = args;
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
/*     */   public CatalogEntry(int type, Vector args) throws CatalogException {
/*     */     try {
/* 197 */       Integer iArgs = entryArgs.get(type);
/* 198 */       if (iArgs.intValue() != args.size()) {
/* 199 */         throw new CatalogException(2);
/*     */       }
/* 201 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 202 */       throw new CatalogException(3);
/*     */     } 
/*     */     
/* 205 */     this.entryType = type;
/* 206 */     this.args = args;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEntryType() {
/* 215 */     return this.entryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEntryArg(int argNum) {
/*     */     try {
/* 227 */       String arg = this.args.get(argNum);
/* 228 */       return arg;
/* 229 */     } catch (ArrayIndexOutOfBoundsException e) {
/* 230 */       return null;
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
/*     */   public void setEntryArg(int argNum, String newspec) throws ArrayIndexOutOfBoundsException {
/* 249 */     this.args.set(argNum, newspec);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/CatalogEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */