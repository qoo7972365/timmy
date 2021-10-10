/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.spi.CharsetProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import sun.misc.ASCIICaseInsensitiveComparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractCharsetProvider
/*     */   extends CharsetProvider
/*     */ {
/*  51 */   private Map<String, String> classMap = new TreeMap<>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Map<String, String> aliasMap = new TreeMap<>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private Map<String, String[]> aliasNameMap = (Map)new TreeMap<>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private Map<String, SoftReference<Charset>> cache = new TreeMap<>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */   
/*     */   private String packagePrefix;
/*     */ 
/*     */   
/*     */   protected AbstractCharsetProvider() {
/*  72 */     this.packagePrefix = "sun.nio.cs";
/*     */   }
/*     */   
/*     */   protected AbstractCharsetProvider(String paramString) {
/*  76 */     this.packagePrefix = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, V> void put(Map<K, V> paramMap, K paramK, V paramV) {
/*  83 */     if (!paramMap.containsKey(paramK))
/*  84 */       paramMap.put(paramK, paramV); 
/*     */   }
/*     */   
/*     */   private static <K, V> void remove(Map<K, V> paramMap, K paramK) {
/*  88 */     V v = paramMap.remove(paramK);
/*  89 */     assert v != null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void charset(String paramString1, String paramString2, String[] paramArrayOfString) {
/*  95 */     synchronized (this) {
/*  96 */       put(this.classMap, paramString1, paramString2);
/*  97 */       for (byte b = 0; b < paramArrayOfString.length; b++)
/*  98 */         put(this.aliasMap, paramArrayOfString[b], paramString1); 
/*  99 */       put((Map)this.aliasNameMap, paramString1, paramArrayOfString);
/* 100 */       this.cache.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void deleteCharset(String paramString, String[] paramArrayOfString) {
/* 105 */     synchronized (this) {
/* 106 */       remove(this.classMap, paramString);
/* 107 */       for (byte b = 0; b < paramArrayOfString.length; b++)
/* 108 */         remove(this.aliasMap, paramArrayOfString[b]); 
/* 109 */       remove((Map)this.aliasNameMap, paramString);
/* 110 */       this.cache.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {}
/*     */ 
/*     */   
/*     */   private String canonicalize(String paramString) {
/* 119 */     String str = this.aliasMap.get(paramString);
/* 120 */     return (str != null) ? str : paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Charset lookup(String paramString) {
/* 126 */     SoftReference<Charset> softReference = this.cache.get(paramString);
/* 127 */     if (softReference != null) {
/* 128 */       Charset charset = softReference.get();
/* 129 */       if (charset != null) {
/* 130 */         return charset;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     String str = this.classMap.get(paramString);
/*     */     
/* 136 */     if (str == null) {
/* 137 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 142 */       Class<?> clazz = Class.forName(this.packagePrefix + "." + str, true, 
/*     */           
/* 144 */           getClass().getClassLoader());
/*     */       
/* 146 */       Charset charset = (Charset)clazz.newInstance();
/* 147 */       this.cache.put(paramString, new SoftReference<>(charset));
/* 148 */       return charset;
/* 149 */     } catch (ClassNotFoundException classNotFoundException) {
/* 150 */       return null;
/* 151 */     } catch (IllegalAccessException illegalAccessException) {
/* 152 */       return null;
/* 153 */     } catch (InstantiationException instantiationException) {
/* 154 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Charset charsetForName(String paramString) {
/* 159 */     synchronized (this) {
/* 160 */       init();
/* 161 */       return lookup(canonicalize(paramString));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterator<Charset> charsets() {
/*     */     final ArrayList ks;
/* 168 */     synchronized (this) {
/* 169 */       init();
/* 170 */       arrayList = new ArrayList(this.classMap.keySet());
/*     */     } 
/*     */     
/* 173 */     return new Iterator<Charset>() {
/* 174 */         Iterator<String> i = ks.iterator();
/*     */         
/*     */         public boolean hasNext() {
/* 177 */           return this.i.hasNext();
/*     */         }
/*     */         
/*     */         public Charset next() {
/* 181 */           String str = this.i.next();
/* 182 */           synchronized (AbstractCharsetProvider.this) {
/* 183 */             return AbstractCharsetProvider.this.lookup(str);
/*     */           } 
/*     */         }
/*     */         
/*     */         public void remove() {
/* 188 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public final String[] aliases(String paramString) {
/* 194 */     synchronized (this) {
/* 195 */       init();
/* 196 */       return this.aliasNameMap.get(paramString);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/AbstractCharsetProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */