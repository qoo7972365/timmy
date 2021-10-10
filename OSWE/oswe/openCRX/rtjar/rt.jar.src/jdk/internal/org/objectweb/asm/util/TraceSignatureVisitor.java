/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceSignatureVisitor
/*     */   extends SignatureVisitor
/*     */ {
/*     */   private final StringBuffer declaration;
/*     */   private boolean isInterface;
/*     */   private boolean seenFormalParameter;
/*     */   private boolean seenInterfaceBound;
/*     */   private boolean seenParameter;
/*     */   private boolean seenInterface;
/*     */   private StringBuffer returnType;
/*     */   private StringBuffer exceptions;
/*     */   private int argumentStack;
/*     */   private int arrayStack;
/* 104 */   private String separator = "";
/*     */   
/*     */   public TraceSignatureVisitor(int paramInt) {
/* 107 */     super(327680);
/* 108 */     this.isInterface = ((paramInt & 0x200) != 0);
/* 109 */     this.declaration = new StringBuffer();
/*     */   }
/*     */   
/*     */   private TraceSignatureVisitor(StringBuffer paramStringBuffer) {
/* 113 */     super(327680);
/* 114 */     this.declaration = paramStringBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String paramString) {
/* 119 */     this.declaration.append(this.seenFormalParameter ? ", " : "<").append(paramString);
/* 120 */     this.seenFormalParameter = true;
/* 121 */     this.seenInterfaceBound = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 126 */     this.separator = " extends ";
/* 127 */     startType();
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 133 */     this.separator = this.seenInterfaceBound ? ", " : " extends ";
/* 134 */     this.seenInterfaceBound = true;
/* 135 */     startType();
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 141 */     endFormals();
/* 142 */     this.separator = " extends ";
/* 143 */     startType();
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 149 */     this.separator = this.seenInterface ? ", " : (this.isInterface ? " extends " : " implements ");
/*     */     
/* 151 */     this.seenInterface = true;
/* 152 */     startType();
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 158 */     endFormals();
/* 159 */     if (this.seenParameter) {
/* 160 */       this.declaration.append(", ");
/*     */     } else {
/* 162 */       this.seenParameter = true;
/* 163 */       this.declaration.append('(');
/*     */     } 
/* 165 */     startType();
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 171 */     endFormals();
/* 172 */     if (this.seenParameter) {
/* 173 */       this.seenParameter = false;
/*     */     } else {
/* 175 */       this.declaration.append('(');
/*     */     } 
/* 177 */     this.declaration.append(')');
/* 178 */     this.returnType = new StringBuffer();
/* 179 */     return new TraceSignatureVisitor(this.returnType);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 184 */     if (this.exceptions == null) {
/* 185 */       this.exceptions = new StringBuffer();
/*     */     } else {
/* 187 */       this.exceptions.append(", ");
/*     */     } 
/*     */     
/* 190 */     return new TraceSignatureVisitor(this.exceptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBaseType(char paramChar) {
/* 195 */     switch (paramChar) {
/*     */       case 'V':
/* 197 */         this.declaration.append("void");
/*     */         break;
/*     */       case 'B':
/* 200 */         this.declaration.append("byte");
/*     */         break;
/*     */       case 'J':
/* 203 */         this.declaration.append("long");
/*     */         break;
/*     */       case 'Z':
/* 206 */         this.declaration.append("boolean");
/*     */         break;
/*     */       case 'I':
/* 209 */         this.declaration.append("int");
/*     */         break;
/*     */       case 'S':
/* 212 */         this.declaration.append("short");
/*     */         break;
/*     */       case 'C':
/* 215 */         this.declaration.append("char");
/*     */         break;
/*     */       case 'F':
/* 218 */         this.declaration.append("float");
/*     */         break;
/*     */       
/*     */       default:
/* 222 */         this.declaration.append("double");
/*     */         break;
/*     */     } 
/* 225 */     endType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String paramString) {
/* 230 */     this.declaration.append(paramString);
/* 231 */     endType();
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 236 */     startType();
/* 237 */     this.arrayStack |= 0x1;
/* 238 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String paramString) {
/* 243 */     if ("java/lang/Object".equals(paramString)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       boolean bool = (this.argumentStack % 2 != 0 || this.seenParameter) ? true : false;
/* 250 */       if (bool) {
/* 251 */         this.declaration.append(this.separator).append(paramString.replace('/', '.'));
/*     */       }
/*     */     } else {
/* 254 */       this.declaration.append(this.separator).append(paramString.replace('/', '.'));
/*     */     } 
/* 256 */     this.separator = "";
/* 257 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String paramString) {
/* 262 */     if (this.argumentStack % 2 != 0) {
/* 263 */       this.declaration.append('>');
/*     */     }
/* 265 */     this.argumentStack /= 2;
/* 266 */     this.declaration.append('.');
/* 267 */     this.declaration.append(this.separator).append(paramString.replace('/', '.'));
/* 268 */     this.separator = "";
/* 269 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 274 */     if (this.argumentStack % 2 == 0) {
/* 275 */       this.argumentStack++;
/* 276 */       this.declaration.append('<');
/*     */     } else {
/* 278 */       this.declaration.append(", ");
/*     */     } 
/* 280 */     this.declaration.append('?');
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char paramChar) {
/* 285 */     if (this.argumentStack % 2 == 0) {
/* 286 */       this.argumentStack++;
/* 287 */       this.declaration.append('<');
/*     */     } else {
/* 289 */       this.declaration.append(", ");
/*     */     } 
/*     */     
/* 292 */     if (paramChar == '+') {
/* 293 */       this.declaration.append("? extends ");
/* 294 */     } else if (paramChar == '-') {
/* 295 */       this.declaration.append("? super ");
/*     */     } 
/*     */     
/* 298 */     startType();
/* 299 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 304 */     if (this.argumentStack % 2 != 0) {
/* 305 */       this.declaration.append('>');
/*     */     }
/* 307 */     this.argumentStack /= 2;
/* 308 */     endType();
/*     */   }
/*     */   
/*     */   public String getDeclaration() {
/* 312 */     return this.declaration.toString();
/*     */   }
/*     */   
/*     */   public String getReturnType() {
/* 316 */     return (this.returnType == null) ? null : this.returnType.toString();
/*     */   }
/*     */   
/*     */   public String getExceptions() {
/* 320 */     return (this.exceptions == null) ? null : this.exceptions.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void endFormals() {
/* 326 */     if (this.seenFormalParameter) {
/* 327 */       this.declaration.append('>');
/* 328 */       this.seenFormalParameter = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startType() {
/* 333 */     this.arrayStack *= 2;
/*     */   }
/*     */   
/*     */   private void endType() {
/* 337 */     if (this.arrayStack % 2 == 0) {
/* 338 */       this.arrayStack /= 2;
/*     */     } else {
/* 340 */       while (this.arrayStack % 2 != 0) {
/* 341 */         this.arrayStack /= 2;
/* 342 */         this.declaration.append("[]");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/TraceSignatureVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */