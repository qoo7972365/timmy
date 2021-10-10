/*     */ package jdk.internal.org.objectweb.asm.signature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureReader
/*     */ {
/*     */   private final String signature;
/*     */   
/*     */   public SignatureReader(String paramString) {
/*  83 */     this.signature = paramString;
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
/*     */   public void accept(SignatureVisitor paramSignatureVisitor) {
/*     */     int j;
/* 102 */     String str = this.signature;
/* 103 */     int i = str.length();
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (str.charAt(0) == '<') {
/* 108 */       char c; j = 2;
/*     */       do {
/* 110 */         int k = str.indexOf(':', j);
/* 111 */         paramSignatureVisitor.visitFormalTypeParameter(str.substring(j - 1, k));
/* 112 */         j = k + 1;
/*     */         
/* 114 */         c = str.charAt(j);
/* 115 */         if (c == 'L' || c == '[' || c == 'T') {
/* 116 */           j = parseType(str, j, paramSignatureVisitor.visitClassBound());
/*     */         }
/*     */         
/* 119 */         while ((c = str.charAt(j++)) == ':') {
/* 120 */           j = parseType(str, j, paramSignatureVisitor.visitInterfaceBound());
/*     */         }
/* 122 */       } while (c != '>');
/*     */     } else {
/* 124 */       j = 0;
/*     */     } 
/*     */     
/* 127 */     if (str.charAt(j) == '(') {
/* 128 */       j++;
/* 129 */       while (str.charAt(j) != ')') {
/* 130 */         j = parseType(str, j, paramSignatureVisitor.visitParameterType());
/*     */       }
/* 132 */       j = parseType(str, j + 1, paramSignatureVisitor.visitReturnType());
/* 133 */       while (j < i) {
/* 134 */         j = parseType(str, j + 1, paramSignatureVisitor.visitExceptionType());
/*     */       }
/*     */     } else {
/* 137 */       j = parseType(str, j, paramSignatureVisitor.visitSuperclass());
/* 138 */       while (j < i) {
/* 139 */         j = parseType(str, j, paramSignatureVisitor.visitInterface());
/*     */       }
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
/*     */   public void acceptType(SignatureVisitor paramSignatureVisitor) {
/* 159 */     parseType(this.signature, 0, paramSignatureVisitor);
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
/*     */   private static int parseType(String paramString, int paramInt, SignatureVisitor paramSignatureVisitor) {
/*     */     int j;
/*     */     char c;
/* 180 */     switch (c = paramString.charAt(paramInt++)) {
/*     */       case 'B':
/*     */       case 'C':
/*     */       case 'D':
/*     */       case 'F':
/*     */       case 'I':
/*     */       case 'J':
/*     */       case 'S':
/*     */       case 'V':
/*     */       case 'Z':
/* 190 */         paramSignatureVisitor.visitBaseType(c);
/* 191 */         return paramInt;
/*     */       
/*     */       case '[':
/* 194 */         return parseType(paramString, paramInt, paramSignatureVisitor.visitArrayType());
/*     */       
/*     */       case 'T':
/* 197 */         j = paramString.indexOf(';', paramInt);
/* 198 */         paramSignatureVisitor.visitTypeVariable(paramString.substring(paramInt, j));
/* 199 */         return j + 1;
/*     */     } 
/*     */     
/* 202 */     int i = paramInt;
/* 203 */     boolean bool1 = false;
/* 204 */     boolean bool2 = false; label36: while (true) {
/*     */       String str;
/* 206 */       switch (c = paramString.charAt(paramInt++)) {
/*     */         case '.':
/*     */         case ';':
/* 209 */           if (!bool1) {
/* 210 */             String str1 = paramString.substring(i, paramInt - 1);
/* 211 */             if (bool2) {
/* 212 */               paramSignatureVisitor.visitInnerClassType(str1);
/*     */             } else {
/* 214 */               paramSignatureVisitor.visitClassType(str1);
/*     */             } 
/*     */           } 
/* 217 */           if (c == ';') {
/* 218 */             paramSignatureVisitor.visitEnd();
/* 219 */             return paramInt;
/*     */           } 
/* 221 */           i = paramInt;
/* 222 */           bool1 = false;
/* 223 */           bool2 = true;
/*     */ 
/*     */         
/*     */         case '<':
/* 227 */           str = paramString.substring(i, paramInt - 1);
/* 228 */           if (bool2) {
/* 229 */             paramSignatureVisitor.visitInnerClassType(str);
/*     */           } else {
/* 231 */             paramSignatureVisitor.visitClassType(str);
/*     */           } 
/* 233 */           bool1 = true;
/*     */           while (true) {
/* 235 */             switch (c = paramString.charAt(paramInt)) {
/*     */               case '>':
/*     */                 continue label36;
/*     */               case '*':
/* 239 */                 paramInt++;
/* 240 */                 paramSignatureVisitor.visitTypeArgument();
/*     */                 continue;
/*     */               case '+':
/*     */               case '-':
/* 244 */                 paramInt = parseType(paramString, paramInt + 1, paramSignatureVisitor
/* 245 */                     .visitTypeArgument(c));
/*     */                 continue;
/*     */             } 
/* 248 */             paramInt = parseType(paramString, paramInt, paramSignatureVisitor
/* 249 */                 .visitTypeArgument('='));
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/signature/SignatureReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */