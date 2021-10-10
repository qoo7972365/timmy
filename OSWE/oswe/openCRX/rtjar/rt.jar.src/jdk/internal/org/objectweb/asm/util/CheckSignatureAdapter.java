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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckSignatureAdapter
/*     */   extends SignatureVisitor
/*     */ {
/*     */   public static final int CLASS_SIGNATURE = 0;
/*     */   public static final int METHOD_SIGNATURE = 1;
/*     */   public static final int TYPE_SIGNATURE = 2;
/*     */   private static final int EMPTY = 1;
/*     */   private static final int FORMAL = 2;
/*     */   private static final int BOUND = 4;
/*     */   private static final int SUPER = 8;
/*     */   private static final int PARAM = 16;
/*     */   private static final int RETURN = 32;
/*     */   private static final int SIMPLE_TYPE = 64;
/*     */   private static final int CLASS_TYPE = 128;
/*     */   private static final int END = 256;
/*     */   private final int type;
/*     */   private int state;
/*     */   private boolean canBeVoid;
/*     */   private final SignatureVisitor sv;
/*     */   
/*     */   public CheckSignatureAdapter(int paramInt, SignatureVisitor paramSignatureVisitor) {
/* 145 */     this(327680, paramInt, paramSignatureVisitor);
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
/*     */   protected CheckSignatureAdapter(int paramInt1, int paramInt2, SignatureVisitor paramSignatureVisitor) {
/* 164 */     super(paramInt1);
/* 165 */     this.type = paramInt2;
/* 166 */     this.state = 1;
/* 167 */     this.sv = paramSignatureVisitor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String paramString) {
/* 174 */     if (this.type == 2 || (this.state != 1 && this.state != 2 && this.state != 4))
/*     */     {
/* 176 */       throw new IllegalStateException();
/*     */     }
/* 178 */     CheckMethodAdapter.checkIdentifier(paramString, "formal type parameter");
/* 179 */     this.state = 2;
/* 180 */     if (this.sv != null) {
/* 181 */       this.sv.visitFormalTypeParameter(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 187 */     if (this.state != 2) {
/* 188 */       throw new IllegalStateException();
/*     */     }
/* 190 */     this.state = 4;
/* 191 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitClassBound();
/* 192 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 197 */     if (this.state != 2 && this.state != 4) {
/* 198 */       throw new IllegalArgumentException();
/*     */     }
/* 200 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitInterfaceBound();
/* 201 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 208 */     if (this.type != 0 || (this.state & 0x7) == 0) {
/* 209 */       throw new IllegalArgumentException();
/*     */     }
/* 211 */     this.state = 8;
/* 212 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitSuperclass();
/* 213 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 218 */     if (this.state != 8) {
/* 219 */       throw new IllegalStateException();
/*     */     }
/* 221 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitInterface();
/* 222 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 229 */     if (this.type != 1 || (this.state & 0x17) == 0)
/*     */     {
/* 231 */       throw new IllegalArgumentException();
/*     */     }
/* 233 */     this.state = 16;
/* 234 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitParameterType();
/* 235 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 240 */     if (this.type != 1 || (this.state & 0x17) == 0)
/*     */     {
/* 242 */       throw new IllegalArgumentException();
/*     */     }
/* 244 */     this.state = 32;
/* 245 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitReturnType();
/* 246 */     CheckSignatureAdapter checkSignatureAdapter = new CheckSignatureAdapter(2, signatureVisitor);
/* 247 */     checkSignatureAdapter.canBeVoid = true;
/* 248 */     return checkSignatureAdapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 253 */     if (this.state != 32) {
/* 254 */       throw new IllegalStateException();
/*     */     }
/* 256 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitExceptionType();
/* 257 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitBaseType(char paramChar) {
/* 264 */     if (this.type != 2 || this.state != 1) {
/* 265 */       throw new IllegalStateException();
/*     */     }
/* 267 */     if (paramChar == 'V') {
/* 268 */       if (!this.canBeVoid) {
/* 269 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 272 */     else if ("ZCBSIFJD".indexOf(paramChar) == -1) {
/* 273 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 276 */     this.state = 64;
/* 277 */     if (this.sv != null) {
/* 278 */       this.sv.visitBaseType(paramChar);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String paramString) {
/* 284 */     if (this.type != 2 || this.state != 1) {
/* 285 */       throw new IllegalStateException();
/*     */     }
/* 287 */     CheckMethodAdapter.checkIdentifier(paramString, "type variable");
/* 288 */     this.state = 64;
/* 289 */     if (this.sv != null) {
/* 290 */       this.sv.visitTypeVariable(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 296 */     if (this.type != 2 || this.state != 1) {
/* 297 */       throw new IllegalStateException();
/*     */     }
/* 299 */     this.state = 64;
/* 300 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitArrayType();
/* 301 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String paramString) {
/* 306 */     if (this.type != 2 || this.state != 1) {
/* 307 */       throw new IllegalStateException();
/*     */     }
/* 309 */     CheckMethodAdapter.checkInternalName(paramString, "class name");
/* 310 */     this.state = 128;
/* 311 */     if (this.sv != null) {
/* 312 */       this.sv.visitClassType(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String paramString) {
/* 318 */     if (this.state != 128) {
/* 319 */       throw new IllegalStateException();
/*     */     }
/* 321 */     CheckMethodAdapter.checkIdentifier(paramString, "inner class name");
/* 322 */     if (this.sv != null) {
/* 323 */       this.sv.visitInnerClassType(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 329 */     if (this.state != 128) {
/* 330 */       throw new IllegalStateException();
/*     */     }
/* 332 */     if (this.sv != null) {
/* 333 */       this.sv.visitTypeArgument();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char paramChar) {
/* 339 */     if (this.state != 128) {
/* 340 */       throw new IllegalStateException();
/*     */     }
/* 342 */     if ("+-=".indexOf(paramChar) == -1) {
/* 343 */       throw new IllegalArgumentException();
/*     */     }
/* 345 */     SignatureVisitor signatureVisitor = (this.sv == null) ? null : this.sv.visitTypeArgument(paramChar);
/* 346 */     return new CheckSignatureAdapter(2, signatureVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 351 */     if (this.state != 128) {
/* 352 */       throw new IllegalStateException();
/*     */     }
/* 354 */     this.state = 256;
/* 355 */     if (this.sv != null)
/* 356 */       this.sv.visitEnd(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/CheckSignatureAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */