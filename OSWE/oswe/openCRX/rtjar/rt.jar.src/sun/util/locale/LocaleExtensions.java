/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleExtensions
/*     */ {
/*     */   private final Map<Character, Extension> extensionMap;
/*     */   private final String id;
/*  52 */   public static final LocaleExtensions CALENDAR_JAPANESE = new LocaleExtensions("u-ca-japanese", 
/*     */       
/*  54 */       Character.valueOf('u'), UnicodeLocaleExtension.CA_JAPANESE);
/*     */ 
/*     */   
/*  57 */   public static final LocaleExtensions NUMBER_THAI = new LocaleExtensions("u-nu-thai", 
/*     */       
/*  59 */       Character.valueOf('u'), UnicodeLocaleExtension.NU_THAI);
/*     */ 
/*     */   
/*     */   private LocaleExtensions(String paramString, Character paramCharacter, Extension paramExtension) {
/*  63 */     this.id = paramString;
/*  64 */     this.extensionMap = Collections.singletonMap(paramCharacter, paramExtension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LocaleExtensions(Map<InternalLocaleBuilder.CaseInsensitiveChar, String> paramMap, Set<InternalLocaleBuilder.CaseInsensitiveString> paramSet, Map<InternalLocaleBuilder.CaseInsensitiveString, String> paramMap1) {
/*  73 */     boolean bool1 = !LocaleUtils.isEmpty(paramMap) ? true : false;
/*  74 */     boolean bool2 = !LocaleUtils.isEmpty(paramSet) ? true : false;
/*  75 */     boolean bool3 = !LocaleUtils.isEmpty(paramMap1) ? true : false;
/*     */     
/*  77 */     if (!bool1 && !bool2 && !bool3) {
/*  78 */       this.id = "";
/*  79 */       this.extensionMap = Collections.emptyMap();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  84 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*  85 */     if (bool1) {
/*  86 */       for (Map.Entry<InternalLocaleBuilder.CaseInsensitiveChar, String> entry : paramMap.entrySet()) {
/*  87 */         char c = LocaleUtils.toLower(((InternalLocaleBuilder.CaseInsensitiveChar)entry.getKey()).value());
/*  88 */         String str = (String)entry.getValue();
/*     */         
/*  90 */         if (LanguageTag.isPrivateusePrefixChar(c)) {
/*     */           
/*  92 */           str = InternalLocaleBuilder.removePrivateuseVariant(str);
/*  93 */           if (str == null) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/*  98 */         treeMap.put(Character.valueOf(c), new Extension(c, LocaleUtils.toLowerString(str)));
/*     */       } 
/*     */     }
/*     */     
/* 102 */     if (bool2 || bool3) {
/* 103 */       TreeSet<String> treeSet = null;
/* 104 */       TreeMap<Object, Object> treeMap1 = null;
/*     */       
/* 106 */       if (bool2) {
/* 107 */         treeSet = new TreeSet();
/* 108 */         for (InternalLocaleBuilder.CaseInsensitiveString caseInsensitiveString : paramSet) {
/* 109 */           treeSet.add(LocaleUtils.toLowerString(caseInsensitiveString.value()));
/*     */         }
/*     */       } 
/*     */       
/* 113 */       if (bool3) {
/* 114 */         treeMap1 = new TreeMap<>();
/* 115 */         for (Map.Entry<InternalLocaleBuilder.CaseInsensitiveString, String> entry : paramMap1.entrySet()) {
/* 116 */           String str1 = LocaleUtils.toLowerString(((InternalLocaleBuilder.CaseInsensitiveString)entry.getKey()).value());
/* 117 */           String str2 = LocaleUtils.toLowerString((String)entry.getValue());
/* 118 */           treeMap1.put(str1, str2);
/*     */         } 
/*     */       } 
/*     */       
/* 122 */       UnicodeLocaleExtension unicodeLocaleExtension = new UnicodeLocaleExtension(treeSet, (SortedMap)treeMap1);
/* 123 */       treeMap.put(Character.valueOf('u'), unicodeLocaleExtension);
/*     */     } 
/*     */     
/* 126 */     if (treeMap.isEmpty()) {
/*     */       
/* 128 */       this.id = "";
/* 129 */       this.extensionMap = Collections.emptyMap();
/*     */     } else {
/* 131 */       this.id = toID((SortedMap)treeMap);
/* 132 */       this.extensionMap = (Map)treeMap;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<Character> getKeys() {
/* 137 */     if (this.extensionMap.isEmpty()) {
/* 138 */       return Collections.emptySet();
/*     */     }
/* 140 */     return Collections.unmodifiableSet(this.extensionMap.keySet());
/*     */   }
/*     */   
/*     */   public Extension getExtension(Character paramCharacter) {
/* 144 */     return this.extensionMap.get(Character.valueOf(LocaleUtils.toLower(paramCharacter.charValue())));
/*     */   }
/*     */   
/*     */   public String getExtensionValue(Character paramCharacter) {
/* 148 */     Extension extension = this.extensionMap.get(Character.valueOf(LocaleUtils.toLower(paramCharacter.charValue())));
/* 149 */     if (extension == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     return extension.getValue();
/*     */   }
/*     */   
/*     */   public Set<String> getUnicodeLocaleAttributes() {
/* 156 */     Extension extension = this.extensionMap.get(Character.valueOf('u'));
/* 157 */     if (extension == null) {
/* 158 */       return Collections.emptySet();
/*     */     }
/* 160 */     assert extension instanceof UnicodeLocaleExtension;
/* 161 */     return ((UnicodeLocaleExtension)extension).getUnicodeLocaleAttributes();
/*     */   }
/*     */   
/*     */   public Set<String> getUnicodeLocaleKeys() {
/* 165 */     Extension extension = this.extensionMap.get(Character.valueOf('u'));
/* 166 */     if (extension == null) {
/* 167 */       return Collections.emptySet();
/*     */     }
/* 169 */     assert extension instanceof UnicodeLocaleExtension;
/* 170 */     return ((UnicodeLocaleExtension)extension).getUnicodeLocaleKeys();
/*     */   }
/*     */   
/*     */   public String getUnicodeLocaleType(String paramString) {
/* 174 */     Extension extension = this.extensionMap.get(Character.valueOf('u'));
/* 175 */     if (extension == null) {
/* 176 */       return null;
/*     */     }
/* 178 */     assert extension instanceof UnicodeLocaleExtension;
/* 179 */     return ((UnicodeLocaleExtension)extension).getUnicodeLocaleType(LocaleUtils.toLowerString(paramString));
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 183 */     return this.extensionMap.isEmpty();
/*     */   }
/*     */   
/*     */   public static boolean isValidKey(char paramChar) {
/* 187 */     return (LanguageTag.isExtensionSingletonChar(paramChar) || LanguageTag.isPrivateusePrefixChar(paramChar));
/*     */   }
/*     */   
/*     */   public static boolean isValidUnicodeLocaleKey(String paramString) {
/* 191 */     return UnicodeLocaleExtension.isKey(paramString);
/*     */   }
/*     */   
/*     */   private static String toID(SortedMap<Character, Extension> paramSortedMap) {
/* 195 */     StringBuilder stringBuilder = new StringBuilder();
/* 196 */     Extension extension = null;
/* 197 */     for (Map.Entry<Character, Extension> entry : paramSortedMap.entrySet()) {
/* 198 */       char c = ((Character)entry.getKey()).charValue();
/* 199 */       Extension extension1 = (Extension)entry.getValue();
/* 200 */       if (LanguageTag.isPrivateusePrefixChar(c)) {
/* 201 */         extension = extension1; continue;
/*     */       } 
/* 203 */       if (stringBuilder.length() > 0) {
/* 204 */         stringBuilder.append("-");
/*     */       }
/* 206 */       stringBuilder.append(extension1);
/*     */     } 
/*     */     
/* 209 */     if (extension != null) {
/* 210 */       if (stringBuilder.length() > 0) {
/* 211 */         stringBuilder.append("-");
/*     */       }
/* 213 */       stringBuilder.append(extension);
/*     */     } 
/* 215 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 224 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 229 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 234 */     if (this == paramObject) {
/* 235 */       return true;
/*     */     }
/* 237 */     if (!(paramObject instanceof LocaleExtensions)) {
/* 238 */       return false;
/*     */     }
/* 240 */     return this.id.equals(((LocaleExtensions)paramObject).id);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LocaleExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */