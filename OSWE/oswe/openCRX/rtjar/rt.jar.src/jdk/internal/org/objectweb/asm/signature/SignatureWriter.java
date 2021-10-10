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
/*     */ public class SignatureWriter
/*     */   extends SignatureVisitor
/*     */ {
/*  74 */   private final StringBuffer buf = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasFormals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int argumentStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureWriter() {
/*  98 */     super(327680);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String paramString) {
/* 107 */     if (!this.hasFormals) {
/* 108 */       this.hasFormals = true;
/* 109 */       this.buf.append('<');
/*     */     } 
/* 111 */     this.buf.append(paramString);
/* 112 */     this.buf.append(':');
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 122 */     this.buf.append(':');
/* 123 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 128 */     endFormals();
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 139 */     endFormals();
/* 140 */     if (!this.hasParameters) {
/* 141 */       this.hasParameters = true;
/* 142 */       this.buf.append('(');
/*     */     } 
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 149 */     endFormals();
/* 150 */     if (!this.hasParameters) {
/* 151 */       this.buf.append('(');
/*     */     }
/* 153 */     this.buf.append(')');
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 159 */     this.buf.append('^');
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBaseType(char paramChar) {
/* 165 */     this.buf.append(paramChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String paramString) {
/* 170 */     this.buf.append('T');
/* 171 */     this.buf.append(paramString);
/* 172 */     this.buf.append(';');
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 177 */     this.buf.append('[');
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String paramString) {
/* 183 */     this.buf.append('L');
/* 184 */     this.buf.append(paramString);
/* 185 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String paramString) {
/* 190 */     endArguments();
/* 191 */     this.buf.append('.');
/* 192 */     this.buf.append(paramString);
/* 193 */     this.argumentStack *= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 198 */     if (this.argumentStack % 2 == 0) {
/* 199 */       this.argumentStack++;
/* 200 */       this.buf.append('<');
/*     */     } 
/* 202 */     this.buf.append('*');
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char paramChar) {
/* 207 */     if (this.argumentStack % 2 == 0) {
/* 208 */       this.argumentStack++;
/* 209 */       this.buf.append('<');
/*     */     } 
/* 211 */     if (paramChar != '=') {
/* 212 */       this.buf.append(paramChar);
/*     */     }
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 219 */     endArguments();
/* 220 */     this.buf.append(';');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 230 */     return this.buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endFormals() {
/* 241 */     if (this.hasFormals) {
/* 242 */       this.hasFormals = false;
/* 243 */       this.buf.append('>');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endArguments() {
/* 251 */     if (this.argumentStack % 2 != 0) {
/* 252 */       this.buf.append('>');
/*     */     }
/* 254 */     this.argumentStack /= 2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/signature/SignatureWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */