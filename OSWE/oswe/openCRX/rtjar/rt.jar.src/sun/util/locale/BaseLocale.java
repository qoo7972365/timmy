/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BaseLocale
/*     */ {
/*     */   public static final String SEP = "_";
/*  41 */   private static final Cache CACHE = new Cache();
/*     */   
/*     */   private final String language;
/*     */   
/*     */   private final String script;
/*     */   private final String region;
/*     */   private final String variant;
/*  48 */   private volatile int hash = 0;
/*     */ 
/*     */   
/*     */   private BaseLocale(String paramString1, String paramString2) {
/*  52 */     this.language = paramString1;
/*  53 */     this.script = "";
/*  54 */     this.region = paramString2;
/*  55 */     this.variant = "";
/*     */   }
/*     */   
/*     */   private BaseLocale(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  59 */     this.language = (paramString1 != null) ? LocaleUtils.toLowerString(paramString1).intern() : "";
/*  60 */     this.script = (paramString2 != null) ? LocaleUtils.toTitleString(paramString2).intern() : "";
/*  61 */     this.region = (paramString3 != null) ? LocaleUtils.toUpperString(paramString3).intern() : "";
/*  62 */     this.variant = (paramString4 != null) ? paramString4.intern() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BaseLocale createInstance(String paramString1, String paramString2) {
/*  68 */     BaseLocale baseLocale = new BaseLocale(paramString1, paramString2);
/*  69 */     CACHE.put(new Key(paramString1, paramString2), baseLocale);
/*  70 */     return baseLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BaseLocale getInstance(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  76 */     if (paramString1 != null) {
/*  77 */       if (LocaleUtils.caseIgnoreMatch(paramString1, "he")) {
/*  78 */         paramString1 = "iw";
/*  79 */       } else if (LocaleUtils.caseIgnoreMatch(paramString1, "yi")) {
/*  80 */         paramString1 = "ji";
/*  81 */       } else if (LocaleUtils.caseIgnoreMatch(paramString1, "id")) {
/*  82 */         paramString1 = "in";
/*     */       } 
/*     */     }
/*     */     
/*  86 */     Key key = new Key(paramString1, paramString2, paramString3, paramString4);
/*  87 */     return CACHE.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/*  92 */     return this.language;
/*     */   }
/*     */   
/*     */   public String getScript() {
/*  96 */     return this.script;
/*     */   }
/*     */   
/*     */   public String getRegion() {
/* 100 */     return this.region;
/*     */   }
/*     */   
/*     */   public String getVariant() {
/* 104 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 109 */     if (this == paramObject) {
/* 110 */       return true;
/*     */     }
/* 112 */     if (!(paramObject instanceof BaseLocale)) {
/* 113 */       return false;
/*     */     }
/* 115 */     BaseLocale baseLocale = (BaseLocale)paramObject;
/* 116 */     return (this.language == baseLocale.language && this.script == baseLocale.script && this.region == baseLocale.region && this.variant == baseLocale.variant);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     StringBuilder stringBuilder = new StringBuilder();
/* 125 */     if (this.language.length() > 0) {
/* 126 */       stringBuilder.append("language=");
/* 127 */       stringBuilder.append(this.language);
/*     */     } 
/* 129 */     if (this.script.length() > 0) {
/* 130 */       if (stringBuilder.length() > 0) {
/* 131 */         stringBuilder.append(", ");
/*     */       }
/* 133 */       stringBuilder.append("script=");
/* 134 */       stringBuilder.append(this.script);
/*     */     } 
/* 136 */     if (this.region.length() > 0) {
/* 137 */       if (stringBuilder.length() > 0) {
/* 138 */         stringBuilder.append(", ");
/*     */       }
/* 140 */       stringBuilder.append("region=");
/* 141 */       stringBuilder.append(this.region);
/*     */     } 
/* 143 */     if (this.variant.length() > 0) {
/* 144 */       if (stringBuilder.length() > 0) {
/* 145 */         stringBuilder.append(", ");
/*     */       }
/* 147 */       stringBuilder.append("variant=");
/* 148 */       stringBuilder.append(this.variant);
/*     */     } 
/* 150 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 155 */     int i = this.hash;
/* 156 */     if (i == 0) {
/*     */       
/* 158 */       i = this.language.hashCode();
/* 159 */       i = 31 * i + this.script.hashCode();
/* 160 */       i = 31 * i + this.region.hashCode();
/* 161 */       i = 31 * i + this.variant.hashCode();
/* 162 */       this.hash = i;
/*     */     } 
/* 164 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Key
/*     */   {
/*     */     private final SoftReference<String> lang;
/*     */     
/*     */     private final SoftReference<String> scrt;
/*     */     
/*     */     private final SoftReference<String> regn;
/*     */     private final SoftReference<String> vart;
/*     */     private final boolean normalized;
/*     */     private final int hash;
/*     */     
/*     */     private Key(String param1String1, String param1String2) {
/* 180 */       assert param1String1.intern() == param1String1 && param1String2
/* 181 */         .intern() == param1String2;
/*     */       
/* 183 */       this.lang = new SoftReference<>(param1String1);
/* 184 */       this.scrt = new SoftReference<>("");
/* 185 */       this.regn = new SoftReference<>(param1String2);
/* 186 */       this.vart = new SoftReference<>("");
/* 187 */       this.normalized = true;
/*     */       
/* 189 */       int i = param1String1.hashCode();
/* 190 */       if (param1String2 != "") {
/* 191 */         int j = param1String2.length();
/* 192 */         for (byte b = 0; b < j; b++) {
/* 193 */           i = 31 * i + LocaleUtils.toLower(param1String2.charAt(b));
/*     */         }
/*     */       } 
/* 196 */       this.hash = i;
/*     */     }
/*     */     
/*     */     public Key(String param1String1, String param1String2, String param1String3, String param1String4) {
/* 200 */       this(param1String1, param1String2, param1String3, param1String4, false);
/*     */     }
/*     */ 
/*     */     
/*     */     private Key(String param1String1, String param1String2, String param1String3, String param1String4, boolean param1Boolean) {
/* 205 */       int i = 0;
/* 206 */       if (param1String1 != null) {
/* 207 */         this.lang = new SoftReference<>(param1String1);
/* 208 */         int j = param1String1.length();
/* 209 */         for (byte b = 0; b < j; b++) {
/* 210 */           i = 31 * i + LocaleUtils.toLower(param1String1.charAt(b));
/*     */         }
/*     */       } else {
/* 213 */         this.lang = new SoftReference<>("");
/*     */       } 
/* 215 */       if (param1String2 != null) {
/* 216 */         this.scrt = new SoftReference<>(param1String2);
/* 217 */         int j = param1String2.length();
/* 218 */         for (byte b = 0; b < j; b++) {
/* 219 */           i = 31 * i + LocaleUtils.toLower(param1String2.charAt(b));
/*     */         }
/*     */       } else {
/* 222 */         this.scrt = new SoftReference<>("");
/*     */       } 
/* 224 */       if (param1String3 != null) {
/* 225 */         this.regn = new SoftReference<>(param1String3);
/* 226 */         int j = param1String3.length();
/* 227 */         for (byte b = 0; b < j; b++) {
/* 228 */           i = 31 * i + LocaleUtils.toLower(param1String3.charAt(b));
/*     */         }
/*     */       } else {
/* 231 */         this.regn = new SoftReference<>("");
/*     */       } 
/* 233 */       if (param1String4 != null) {
/* 234 */         this.vart = new SoftReference<>(param1String4);
/* 235 */         int j = param1String4.length();
/* 236 */         for (byte b = 0; b < j; b++) {
/* 237 */           i = 31 * i + param1String4.charAt(b);
/*     */         }
/*     */       } else {
/* 240 */         this.vart = new SoftReference<>("");
/*     */       } 
/* 242 */       this.hash = i;
/* 243 */       this.normalized = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 248 */       if (this == param1Object) {
/* 249 */         return true;
/*     */       }
/*     */       
/* 252 */       if (param1Object instanceof Key && this.hash == ((Key)param1Object).hash) {
/* 253 */         String str1 = this.lang.get();
/* 254 */         String str2 = ((Key)param1Object).lang.get();
/* 255 */         if (str1 != null && str2 != null && 
/* 256 */           LocaleUtils.caseIgnoreMatch(str2, str1)) {
/* 257 */           String str3 = this.scrt.get();
/* 258 */           String str4 = ((Key)param1Object).scrt.get();
/* 259 */           if (str3 != null && str4 != null && 
/* 260 */             LocaleUtils.caseIgnoreMatch(str4, str3)) {
/* 261 */             String str5 = this.regn.get();
/* 262 */             String str6 = ((Key)param1Object).regn.get();
/* 263 */             if (str5 != null && str6 != null && 
/* 264 */               LocaleUtils.caseIgnoreMatch(str6, str5)) {
/* 265 */               String str7 = this.vart.get();
/* 266 */               String str8 = ((Key)param1Object).vart.get();
/* 267 */               return (str8 != null && str8.equals(str7));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 272 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 277 */       return this.hash;
/*     */     }
/*     */     
/*     */     public static Key normalize(Key param1Key) {
/* 281 */       if (param1Key.normalized) {
/* 282 */         return param1Key;
/*     */       }
/*     */       
/* 285 */       String str1 = LocaleUtils.toLowerString(param1Key.lang.get()).intern();
/* 286 */       String str2 = LocaleUtils.toTitleString(param1Key.scrt.get()).intern();
/* 287 */       String str3 = LocaleUtils.toUpperString(param1Key.regn.get()).intern();
/* 288 */       String str4 = ((String)param1Key.vart.get()).intern();
/*     */       
/* 290 */       return new Key(str1, str2, str3, str4, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Cache
/*     */     extends LocaleObjectCache<Key, BaseLocale>
/*     */   {
/*     */     protected BaseLocale.Key normalizeKey(BaseLocale.Key param1Key) {
/* 301 */       assert param1Key.lang.get() != null && param1Key
/* 302 */         .scrt.get() != null && param1Key
/* 303 */         .regn.get() != null && param1Key
/* 304 */         .vart.get() != null;
/*     */       
/* 306 */       return BaseLocale.Key.normalize(param1Key);
/*     */     }
/*     */ 
/*     */     
/*     */     protected BaseLocale createObject(BaseLocale.Key param1Key) {
/* 311 */       return new BaseLocale(param1Key.lang.get(), param1Key.scrt.get(), param1Key
/* 312 */           .regn.get(), param1Key.vart.get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/BaseLocale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */