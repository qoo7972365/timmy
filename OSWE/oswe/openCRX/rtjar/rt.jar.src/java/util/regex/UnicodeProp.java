/*     */ package java.util.regex;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum UnicodeProp
/*     */ {
/*  33 */   ALPHABETIC {
/*     */     public boolean is(int param1Int) {
/*  35 */       return Character.isAlphabetic(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  39 */   LETTER {
/*     */     public boolean is(int param1Int) {
/*  41 */       return Character.isLetter(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  45 */   IDEOGRAPHIC {
/*     */     public boolean is(int param1Int) {
/*  47 */       return Character.isIdeographic(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  51 */   LOWERCASE {
/*     */     public boolean is(int param1Int) {
/*  53 */       return Character.isLowerCase(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  57 */   UPPERCASE {
/*     */     public boolean is(int param1Int) {
/*  59 */       return Character.isUpperCase(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  63 */   TITLECASE {
/*     */     public boolean is(int param1Int) {
/*  65 */       return Character.isTitleCase(param1Int);
/*     */     }
/*     */   },
/*     */   
/*  69 */   WHITE_SPACE
/*     */   {
/*     */     public boolean is(int param1Int) {
/*  72 */       return ((28672 >> 
/*     */         
/*  74 */         Character.getType(param1Int) & 0x1) != 0 || (param1Int >= 9 && param1Int <= 13) || param1Int == 133);
/*     */     }
/*     */   },
/*     */ 
/*     */   
/*  79 */   CONTROL
/*     */   {
/*     */     public boolean is(int param1Int) {
/*  82 */       return (Character.getType(param1Int) == 15);
/*     */     }
/*     */   },
/*     */   
/*  86 */   PUNCTUATION
/*     */   {
/*     */     public boolean is(int param1Int) {
/*  89 */       return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  95 */         ((1643118592 >> Character.getType(param1Int) & 0x1) != 0);
/*     */     }
/*     */   },
/*     */ 
/*     */   
/* 100 */   HEX_DIGIT
/*     */   {
/*     */     public boolean is(int param1Int)
/*     */     {
/* 104 */       return (DIGIT.is(param1Int) || (param1Int >= 48 && param1Int <= 57) || (param1Int >= 65 && param1Int <= 70) || (param1Int >= 97 && param1Int <= 102) || (param1Int >= 65296 && param1Int <= 65305) || (param1Int >= 65313 && param1Int <= 65318) || (param1Int >= 65345 && param1Int <= 65350));
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   ASSIGNED {
/*     */     public boolean is(int param1Int) {
/* 116 */       return (Character.getType(param1Int) != 0);
/*     */     }
/*     */   },
/*     */   
/* 120 */   NONCHARACTER_CODE_POINT
/*     */   {
/*     */     public boolean is(int param1Int) {
/* 123 */       return ((param1Int & 0xFFFE) == 65534 || (param1Int >= 64976 && param1Int <= 65007));
/*     */     }
/*     */   },
/*     */   
/* 127 */   DIGIT
/*     */   {
/*     */     public boolean is(int param1Int) {
/* 130 */       return Character.isDigit(param1Int);
/*     */     }
/*     */   },
/*     */   
/* 134 */   ALNUM
/*     */   {
/*     */     public boolean is(int param1Int)
/*     */     {
/* 138 */       return (ALPHABETIC.is(param1Int) || DIGIT.is(param1Int));
/*     */     }
/*     */   },
/*     */   
/* 142 */   BLANK
/*     */   {
/*     */ 
/*     */     
/*     */     public boolean is(int param1Int)
/*     */     {
/* 148 */       return (Character.getType(param1Int) == 12 || param1Int == 9);
/*     */     }
/*     */   },
/*     */ 
/*     */   
/* 153 */   GRAPH
/*     */   {
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean is(int param1Int)
/*     */     {
/* 160 */       return 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         ((585729 >> Character.getType(param1Int) & 0x1) == 0);
/*     */     }
/*     */   },
/*     */ 
/*     */   
/* 170 */   PRINT
/*     */   {
/*     */     
/*     */     public boolean is(int param1Int)
/*     */     {
/* 175 */       return ((GRAPH.is(param1Int) || BLANK.is(param1Int)) && !CONTROL.is(param1Int));
/*     */     }
/*     */   },
/*     */   
/* 179 */   WORD
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean is(int param1Int)
/*     */     {
/* 187 */       return (ALPHABETIC.is(param1Int) || (8389568 >> 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         Character.getType(param1Int) & 0x1) != 0 || JOIN_CONTROL
/*     */         
/* 194 */         .is(param1Int));
/*     */     }
/*     */   },
/*     */   
/* 198 */   JOIN_CONTROL
/*     */   {
/*     */     public boolean is(int param1Int) {
/* 201 */       return (param1Int == 8204 || param1Int == 8205);
/*     */     } };
/*     */   private static final HashMap<String, String> posix;
/*     */   static {
/* 205 */     posix = new HashMap<>();
/* 206 */     aliases = new HashMap<>();
/*     */     
/* 208 */     posix.put("ALPHA", "ALPHABETIC");
/* 209 */     posix.put("LOWER", "LOWERCASE");
/* 210 */     posix.put("UPPER", "UPPERCASE");
/* 211 */     posix.put("SPACE", "WHITE_SPACE");
/* 212 */     posix.put("PUNCT", "PUNCTUATION");
/* 213 */     posix.put("XDIGIT", "HEX_DIGIT");
/* 214 */     posix.put("ALNUM", "ALNUM");
/* 215 */     posix.put("CNTRL", "CONTROL");
/* 216 */     posix.put("DIGIT", "DIGIT");
/* 217 */     posix.put("BLANK", "BLANK");
/* 218 */     posix.put("GRAPH", "GRAPH");
/* 219 */     posix.put("PRINT", "PRINT");
/*     */     
/* 221 */     aliases.put("WHITESPACE", "WHITE_SPACE");
/* 222 */     aliases.put("HEXDIGIT", "HEX_DIGIT");
/* 223 */     aliases.put("NONCHARACTERCODEPOINT", "NONCHARACTER_CODE_POINT");
/* 224 */     aliases.put("JOINCONTROL", "JOIN_CONTROL");
/*     */   }
/*     */   private static final HashMap<String, String> aliases;
/*     */   public static UnicodeProp forName(String paramString) {
/* 228 */     paramString = paramString.toUpperCase(Locale.ENGLISH);
/* 229 */     String str = aliases.get(paramString);
/* 230 */     if (str != null)
/* 231 */       paramString = str; 
/*     */     try {
/* 233 */       return valueOf(paramString);
/* 234 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 235 */       return null;
/*     */     } 
/*     */   }
/*     */   public static UnicodeProp forPOSIXName(String paramString) {
/* 239 */     paramString = posix.get(paramString.toUpperCase(Locale.ENGLISH));
/* 240 */     if (paramString == null)
/* 241 */       return null; 
/* 242 */     return valueOf(paramString);
/*     */   }
/*     */   
/*     */   public abstract boolean is(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/regex/UnicodeProp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */