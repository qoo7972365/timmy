/*     */ package sun.misc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Regexp
/*     */ {
/*     */   public boolean ignoreCase;
/*     */   public String exp;
/*     */   public String prefix;
/*     */   public String suffix;
/*     */   public boolean exact;
/*     */   public int prefixLen;
/*     */   public int suffixLen;
/*     */   public int totalLen;
/*     */   public String[] mids;
/*     */   
/*     */   public Regexp(String paramString) {
/*  60 */     this.exp = paramString;
/*  61 */     int i = paramString.indexOf('*');
/*  62 */     int j = paramString.lastIndexOf('*');
/*  63 */     if (i < 0) {
/*  64 */       this.totalLen = paramString.length();
/*  65 */       this.exact = true;
/*     */     } else {
/*  67 */       this.prefixLen = i;
/*  68 */       if (i == 0) {
/*  69 */         this.prefix = null;
/*     */       } else {
/*  71 */         this.prefix = paramString.substring(0, i);
/*  72 */       }  this.suffixLen = paramString.length() - j - 1;
/*  73 */       if (this.suffixLen == 0) {
/*  74 */         this.suffix = null;
/*     */       } else {
/*  76 */         this.suffix = paramString.substring(j + 1);
/*  77 */       }  byte b = 0;
/*  78 */       int k = i;
/*  79 */       while (k < j && k >= 0) {
/*  80 */         b++;
/*  81 */         k = paramString.indexOf('*', k + 1);
/*     */       } 
/*  83 */       this.totalLen = this.prefixLen + this.suffixLen;
/*  84 */       if (b > 0) {
/*  85 */         this.mids = new String[b];
/*  86 */         k = i;
/*  87 */         for (byte b1 = 0; b1 < b; b1++) {
/*  88 */           k++;
/*  89 */           int m = paramString.indexOf('*', k);
/*  90 */           if (k < m) {
/*  91 */             this.mids[b1] = paramString.substring(k, m);
/*  92 */             this.totalLen += this.mids[b1].length();
/*     */           } 
/*  94 */           k = m;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean matches(String paramString) {
/* 102 */     return matches(paramString, 0, paramString.length());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean matches(String paramString, int paramInt1, int paramInt2) {
/* 108 */     if (this.exact)
/* 109 */       return (paramInt2 == this.totalLen && this.exp
/* 110 */         .regionMatches(this.ignoreCase, 0, paramString, paramInt1, paramInt2)); 
/* 111 */     if (paramInt2 < this.totalLen)
/* 112 */       return false; 
/* 113 */     if ((this.prefixLen > 0 && 
/* 114 */       !this.prefix.regionMatches(this.ignoreCase, 0, paramString, paramInt1, this.prefixLen)) || (this.suffixLen > 0 && 
/*     */ 
/*     */ 
/*     */       
/* 118 */       !this.suffix.regionMatches(this.ignoreCase, 0, paramString, paramInt1 + paramInt2 - this.suffixLen, this.suffixLen)))
/*     */     {
/*     */       
/* 121 */       return false; } 
/* 122 */     if (this.mids == null)
/* 123 */       return true; 
/* 124 */     int i = this.mids.length;
/* 125 */     int j = paramInt1 + this.prefixLen;
/* 126 */     int k = paramInt1 + paramInt2 - this.suffixLen;
/* 127 */     for (byte b = 0; b < i; b++) {
/* 128 */       String str = this.mids[b];
/* 129 */       int m = str.length();
/* 130 */       while (j + m <= k && 
/* 131 */         !str.regionMatches(this.ignoreCase, 0, paramString, j, m))
/*     */       {
/* 133 */         j++; } 
/* 134 */       if (j + m > k)
/* 135 */         return false; 
/* 136 */       j += m;
/*     */     } 
/* 138 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Regexp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */