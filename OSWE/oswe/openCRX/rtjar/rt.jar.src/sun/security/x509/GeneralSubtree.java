/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneralSubtree
/*     */ {
/*     */   private static final byte TAG_MIN = 0;
/*     */   private static final byte TAG_MAX = 1;
/*     */   private static final int MIN_DEFAULT = 0;
/*     */   private GeneralName name;
/*  51 */   private int minimum = 0;
/*  52 */   private int maximum = -1;
/*     */   
/*  54 */   private int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralSubtree(GeneralName paramGeneralName, int paramInt1, int paramInt2) {
/*  64 */     this.name = paramGeneralName;
/*  65 */     this.minimum = paramInt1;
/*  66 */     this.maximum = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralSubtree(DerValue paramDerValue) throws IOException {
/*  75 */     if (paramDerValue.tag != 48) {
/*  76 */       throw new IOException("Invalid encoding for GeneralSubtree.");
/*     */     }
/*  78 */     this.name = new GeneralName(paramDerValue.data.getDerValue(), true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     while (paramDerValue.data.available() != 0) {
/*  84 */       DerValue derValue = paramDerValue.data.getDerValue();
/*     */       
/*  86 */       if (derValue.isContextSpecific((byte)0) && !derValue.isConstructed()) {
/*  87 */         derValue.resetTag((byte)2);
/*  88 */         this.minimum = derValue.getInteger(); continue;
/*     */       } 
/*  90 */       if (derValue.isContextSpecific((byte)1) && !derValue.isConstructed()) {
/*  91 */         derValue.resetTag((byte)2);
/*  92 */         this.maximum = derValue.getInteger(); continue;
/*     */       } 
/*  94 */       throw new IOException("Invalid encoding of GeneralSubtree.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralName getName() {
/* 105 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimum() {
/* 114 */     return this.minimum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximum() {
/* 123 */     return this.maximum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     String str = "\n   GeneralSubtree: [\n    GeneralName: " + ((this.name == null) ? "" : this.name.toString()) + "\n    Minimum: " + this.minimum;
/*     */     
/* 133 */     if (this.maximum == -1) {
/* 134 */       str = str + "\t    Maximum: undefined";
/*     */     } else {
/* 136 */       str = str + "\t    Maximum: " + this.maximum;
/* 137 */     }  str = str + "    ]\n";
/* 138 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 148 */     if (!(paramObject instanceof GeneralSubtree))
/* 149 */       return false; 
/* 150 */     GeneralSubtree generalSubtree = (GeneralSubtree)paramObject;
/* 151 */     if (this.name == null) {
/* 152 */       if (generalSubtree.name != null) {
/* 153 */         return false;
/*     */       }
/*     */     }
/* 156 */     else if (!this.name.equals(generalSubtree.name)) {
/* 157 */       return false;
/*     */     } 
/* 159 */     if (this.minimum != generalSubtree.minimum)
/* 160 */       return false; 
/* 161 */     if (this.maximum != generalSubtree.maximum)
/* 162 */       return false; 
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 172 */     if (this.myhash == -1) {
/* 173 */       this.myhash = 17;
/* 174 */       if (this.name != null) {
/* 175 */         this.myhash = 37 * this.myhash + this.name.hashCode();
/*     */       }
/* 177 */       if (this.minimum != 0) {
/* 178 */         this.myhash = 37 * this.myhash + this.minimum;
/*     */       }
/* 180 */       if (this.maximum != -1) {
/* 181 */         this.myhash = 37 * this.myhash + this.maximum;
/*     */       }
/*     */     } 
/* 184 */     return this.myhash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 193 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 195 */     this.name.encode(derOutputStream);
/*     */     
/* 197 */     if (this.minimum != 0) {
/* 198 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 199 */       derOutputStream1.putInteger(this.minimum);
/* 200 */       derOutputStream.writeImplicit(DerValue.createTag(-128, false, (byte)0), derOutputStream1);
/*     */     } 
/*     */     
/* 203 */     if (this.maximum != -1) {
/* 204 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 205 */       derOutputStream1.putInteger(this.maximum);
/* 206 */       derOutputStream.writeImplicit(DerValue.createTag(-128, false, (byte)1), derOutputStream1);
/*     */     } 
/*     */     
/* 209 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/GeneralSubtree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */