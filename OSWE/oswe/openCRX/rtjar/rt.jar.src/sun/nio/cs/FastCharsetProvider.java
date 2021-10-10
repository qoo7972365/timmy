/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.spi.CharsetProvider;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastCharsetProvider
/*     */   extends CharsetProvider
/*     */ {
/*     */   private Map<String, String> classMap;
/*     */   private Map<String, String> aliasMap;
/*     */   private Map<String, Charset> cache;
/*     */   private String packagePrefix;
/*     */   
/*     */   protected FastCharsetProvider(String paramString, Map<String, String> paramMap1, Map<String, String> paramMap2, Map<String, Charset> paramMap) {
/*  60 */     this.packagePrefix = paramString;
/*  61 */     this.aliasMap = paramMap1;
/*  62 */     this.classMap = paramMap2;
/*  63 */     this.cache = paramMap;
/*     */   }
/*     */   
/*     */   private String canonicalize(String paramString) {
/*  67 */     String str = this.aliasMap.get(paramString);
/*  68 */     return (str != null) ? str : paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toLower(String paramString) {
/*  74 */     int i = paramString.length();
/*  75 */     boolean bool = true;
/*  76 */     for (byte b1 = 0; b1 < i; b1++) {
/*  77 */       char c = paramString.charAt(b1);
/*  78 */       if ((c - 65 | 90 - c) >= 0) {
/*  79 */         bool = false;
/*     */         break;
/*     */       } 
/*     */     } 
/*  83 */     if (bool)
/*  84 */       return paramString; 
/*  85 */     char[] arrayOfChar = new char[i];
/*  86 */     for (byte b2 = 0; b2 < i; b2++) {
/*  87 */       char c = paramString.charAt(b2);
/*  88 */       if ((c - 65 | 90 - c) >= 0) {
/*  89 */         arrayOfChar[b2] = (char)(c + 32);
/*     */       } else {
/*  91 */         arrayOfChar[b2] = (char)c;
/*     */       } 
/*  93 */     }  return new String(arrayOfChar);
/*     */   }
/*     */ 
/*     */   
/*     */   private Charset lookup(String paramString) {
/*  98 */     String str1 = canonicalize(toLower(paramString));
/*     */ 
/*     */     
/* 101 */     Charset charset = this.cache.get(str1);
/* 102 */     if (charset != null) {
/* 103 */       return charset;
/*     */     }
/*     */     
/* 106 */     String str2 = this.classMap.get(str1);
/* 107 */     if (str2 == null) {
/* 108 */       return null;
/*     */     }
/* 110 */     if (str2.equals("US_ASCII")) {
/* 111 */       charset = new US_ASCII();
/* 112 */       this.cache.put(str1, charset);
/* 113 */       return charset;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 118 */       Class<?> clazz = Class.forName(this.packagePrefix + "." + str2, true, 
/*     */           
/* 120 */           getClass().getClassLoader());
/* 121 */       charset = (Charset)clazz.newInstance();
/* 122 */       this.cache.put(str1, charset);
/* 123 */       return charset;
/* 124 */     } catch (ClassNotFoundException|IllegalAccessException|InstantiationException classNotFoundException) {
/*     */ 
/*     */       
/* 127 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Charset charsetForName(String paramString) {
/* 132 */     synchronized (this) {
/* 133 */       return lookup(canonicalize(paramString));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterator<Charset> charsets() {
/* 139 */     return new Iterator<Charset>()
/*     */       {
/* 141 */         Iterator<String> i = FastCharsetProvider.this.classMap.keySet().iterator();
/*     */         
/*     */         public boolean hasNext() {
/* 144 */           return this.i.hasNext();
/*     */         }
/*     */         
/*     */         public Charset next() {
/* 148 */           String str = this.i.next();
/* 149 */           return FastCharsetProvider.this.lookup(str);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 153 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/FastCharsetProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */