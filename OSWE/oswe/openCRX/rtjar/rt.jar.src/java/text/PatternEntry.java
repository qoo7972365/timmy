/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PatternEntry
/*     */ {
/*     */   static final int RESET = -2;
/*     */   static final int UNSET = -1;
/*     */   int strength;
/*     */   String chars;
/*     */   String extension;
/*     */   
/*     */   public void appendQuotedExtension(StringBuffer paramStringBuffer) {
/*  56 */     appendQuoted(this.extension, paramStringBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendQuotedChars(StringBuffer paramStringBuffer) {
/*  63 */     appendQuoted(this.chars, paramStringBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  72 */     if (paramObject == null) return false; 
/*  73 */     PatternEntry patternEntry = (PatternEntry)paramObject;
/*  74 */     return this.chars.equals(patternEntry.chars);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  79 */     return this.chars.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  86 */     StringBuffer stringBuffer = new StringBuffer();
/*  87 */     addToBuffer(stringBuffer, true, false, null);
/*  88 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getStrength() {
/*  95 */     return this.strength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String getExtension() {
/* 102 */     return this.extension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String getChars() {
/* 109 */     return this.chars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addToBuffer(StringBuffer paramStringBuffer, boolean paramBoolean1, boolean paramBoolean2, PatternEntry paramPatternEntry) {
/* 119 */     if (paramBoolean2 && paramStringBuffer.length() > 0)
/* 120 */       if (this.strength == 0 || paramPatternEntry != null) {
/* 121 */         paramStringBuffer.append('\n');
/*     */       } else {
/* 123 */         paramStringBuffer.append(' ');
/* 124 */       }   if (paramPatternEntry != null) {
/* 125 */       paramStringBuffer.append('&');
/* 126 */       if (paramBoolean2)
/* 127 */         paramStringBuffer.append(' '); 
/* 128 */       paramPatternEntry.appendQuotedChars(paramStringBuffer);
/* 129 */       appendQuotedExtension(paramStringBuffer);
/* 130 */       if (paramBoolean2)
/* 131 */         paramStringBuffer.append(' '); 
/*     */     } 
/* 133 */     switch (this.strength) { case 3:
/* 134 */         paramStringBuffer.append('='); break;
/* 135 */       case 2: paramStringBuffer.append(','); break;
/* 136 */       case 1: paramStringBuffer.append(';'); break;
/* 137 */       case 0: paramStringBuffer.append('<'); break;
/* 138 */       case -2: paramStringBuffer.append('&'); break;
/* 139 */       case -1: paramStringBuffer.append('?'); break; }
/*     */     
/* 141 */     if (paramBoolean2)
/* 142 */       paramStringBuffer.append(' '); 
/* 143 */     appendQuoted(this.chars, paramStringBuffer);
/* 144 */     if (paramBoolean1 && this.extension.length() != 0) {
/* 145 */       paramStringBuffer.append('/');
/* 146 */       appendQuoted(this.extension, paramStringBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void appendQuoted(String paramString, StringBuffer paramStringBuffer) {
/* 151 */     boolean bool = false;
/* 152 */     char c = paramString.charAt(0);
/* 153 */     if (Character.isSpaceChar(c)) {
/* 154 */       bool = true;
/* 155 */       paramStringBuffer.append('\'');
/*     */     }
/* 157 */     else if (isSpecialChar(c)) {
/* 158 */       bool = true;
/* 159 */       paramStringBuffer.append('\'');
/*     */     } else {
/* 161 */       switch (c) { case '\t': case '\n': case '\f': case '\r':
/*     */         case '\020':
/*     */         case '@':
/* 164 */           bool = true;
/* 165 */           paramStringBuffer.append('\'');
/*     */           break;
/*     */         case '\'':
/* 168 */           bool = true;
/* 169 */           paramStringBuffer.append('\'');
/*     */           break;
/*     */         default:
/* 172 */           if (bool) {
/* 173 */             bool = false; paramStringBuffer.append('\'');
/*     */           } 
/*     */           break; }
/*     */ 
/*     */     
/*     */     } 
/* 179 */     paramStringBuffer.append(paramString);
/* 180 */     if (bool) {
/* 181 */       paramStringBuffer.append('\'');
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PatternEntry(int paramInt, StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2) {
/* 303 */     this.strength = -1;
/* 304 */     this.chars = "";
/* 305 */     this.extension = "";
/*     */     this.strength = paramInt;
/*     */     this.chars = paramStringBuffer1.toString();
/*     */     this.extension = (paramStringBuffer2.length() > 0) ? paramStringBuffer2.toString() : "";
/*     */   }
/*     */   
/*     */   static class Parser {
/*     */     private String pattern;
/*     */     private int i;
/*     */     private StringBuffer newChars;
/*     */     private StringBuffer newExtension;
/*     */     
/*     */     public Parser(String param1String) {
/*     */       this.newChars = new StringBuffer();
/*     */       this.newExtension = new StringBuffer();
/*     */       this.pattern = param1String;
/*     */       this.i = 0;
/*     */     }
/*     */     
/*     */     public PatternEntry next() throws ParseException {
/*     */       byte b = -1;
/*     */       this.newChars.setLength(0);
/*     */       this.newExtension.setLength(0);
/*     */       boolean bool1 = true;
/*     */       boolean bool2 = false;
/*     */       while (this.i < this.pattern.length()) {
/*     */         char c = this.pattern.charAt(this.i);
/*     */         if (bool2) {
/*     */           if (c == '\'') {
/*     */             bool2 = false;
/*     */           } else if (this.newChars.length() == 0) {
/*     */             this.newChars.append(c);
/*     */           } else if (bool1) {
/*     */             this.newChars.append(c);
/*     */           } else {
/*     */             this.newExtension.append(c);
/*     */           } 
/*     */         } else {
/*     */           switch (c) {
/*     */             case '=':
/*     */               if (b != -1)
/*     */                 break; 
/*     */               b = 3;
/*     */               break;
/*     */             case ',':
/*     */               if (b != -1)
/*     */                 break; 
/*     */               b = 2;
/*     */               break;
/*     */             case ';':
/*     */               if (b != -1)
/*     */                 break; 
/*     */               b = 1;
/*     */               break;
/*     */             case '<':
/*     */               if (b != -1)
/*     */                 break; 
/*     */               b = 0;
/*     */               break;
/*     */             case '&':
/*     */               if (b != -1)
/*     */                 break; 
/*     */               b = -2;
/*     */               break;
/*     */             case '\t':
/*     */             case '\n':
/*     */             case '\f':
/*     */             case '\r':
/*     */             case ' ':
/*     */               break;
/*     */             case '/':
/*     */               bool1 = false;
/*     */               break;
/*     */             case '\'':
/*     */               bool2 = true;
/*     */               c = this.pattern.charAt(++this.i);
/*     */               if (this.newChars.length() == 0) {
/*     */                 this.newChars.append(c);
/*     */                 break;
/*     */               } 
/*     */               if (bool1) {
/*     */                 this.newChars.append(c);
/*     */                 break;
/*     */               } 
/*     */               this.newExtension.append(c);
/*     */               break;
/*     */             default:
/*     */               if (b == -1)
/*     */                 throw new ParseException("missing char (=,;<&) : " + this.pattern.substring(this.i, (this.i + 10 < this.pattern.length()) ? (this.i + 10) : this.pattern.length()), this.i); 
/*     */               if (PatternEntry.isSpecialChar(c) && !bool2)
/*     */                 throw new ParseException("Unquoted punctuation character : " + Integer.toString(c, 16), this.i); 
/*     */               if (bool1) {
/*     */                 this.newChars.append(c);
/*     */                 break;
/*     */               } 
/*     */               this.newExtension.append(c);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */         this.i++;
/*     */       } 
/*     */       if (b == -1)
/*     */         return null; 
/*     */       if (this.newChars.length() == 0)
/*     */         throw new ParseException("missing chars (=,;<&): " + this.pattern.substring(this.i, (this.i + 10 < this.pattern.length()) ? (this.i + 10) : this.pattern.length()), this.i); 
/*     */       return new PatternEntry(b, this.newChars, this.newExtension);
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean isSpecialChar(char paramChar) {
/*     */     return (paramChar == ' ' || (paramChar <= '/' && paramChar >= '"') || (paramChar <= '?' && paramChar >= ':') || (paramChar <= '`' && paramChar >= '[') || (paramChar <= '~' && paramChar >= '{'));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/PatternEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */