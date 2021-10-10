/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Util
/*     */ {
/*  41 */   private static final Charset jnuEncoding = Charset.forName(
/*  42 */       AccessController.<String>doPrivileged(new GetPropertyAction("sun.jnu.encoding")));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Charset jnuEncoding() {
/*  48 */     return jnuEncoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] toBytes(String paramString) {
/*  56 */     return paramString.getBytes(jnuEncoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toString(byte[] paramArrayOfbyte) {
/*  64 */     return new String(paramArrayOfbyte, jnuEncoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String[] split(String paramString, char paramChar) {
/*  74 */     byte b1 = 0;
/*  75 */     for (byte b2 = 0; b2 < paramString.length(); b2++) {
/*  76 */       if (paramString.charAt(b2) == paramChar)
/*  77 */         b1++; 
/*     */     } 
/*  79 */     String[] arrayOfString = new String[b1 + 1];
/*  80 */     byte b3 = 0;
/*  81 */     int i = 0;
/*  82 */     for (byte b4 = 0; b4 < paramString.length(); b4++) {
/*  83 */       if (paramString.charAt(b4) == paramChar) {
/*  84 */         arrayOfString[b3++] = paramString.substring(i, b4);
/*  85 */         i = b4 + 1;
/*     */       } 
/*     */     } 
/*  88 */     arrayOfString[b3] = paramString.substring(i, paramString.length());
/*  89 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   static <E> Set<E> newSet(E... paramVarArgs) {
/*  97 */     HashSet<E> hashSet = new HashSet();
/*  98 */     for (E e : paramVarArgs) {
/*  99 */       hashSet.add(e);
/*     */     }
/* 101 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   static <E> Set<E> newSet(Set<E> paramSet, E... paramVarArgs) {
/* 110 */     HashSet<E> hashSet = new HashSet<>(paramSet);
/* 111 */     for (E e : paramVarArgs) {
/* 112 */       hashSet.add(e);
/*     */     }
/* 114 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean followLinks(LinkOption... paramVarArgs) {
/* 121 */     boolean bool = true;
/* 122 */     for (LinkOption linkOption : paramVarArgs) {
/* 123 */       if (linkOption == LinkOption.NOFOLLOW_LINKS)
/* 124 */       { bool = false; }
/* 125 */       else { if (linkOption == null) {
/* 126 */           throw new NullPointerException();
/*     */         }
/* 128 */         throw new AssertionError("Should not get here"); }
/*     */     
/*     */     } 
/* 131 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */