/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class EDIPartyName
/*     */   implements GeneralNameInterface
/*     */ {
/*     */   private static final byte TAG_ASSIGNER = 0;
/*     */   private static final byte TAG_PARTYNAME = 1;
/*  51 */   private String assigner = null;
/*  52 */   private String party = null;
/*     */   
/*  54 */   private int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EDIPartyName(String paramString1, String paramString2) {
/*  63 */     this.assigner = paramString1;
/*  64 */     this.party = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EDIPartyName(String paramString) {
/*  73 */     this.party = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EDIPartyName(DerValue paramDerValue) throws IOException {
/*  83 */     DerInputStream derInputStream = new DerInputStream(paramDerValue.toByteArray());
/*  84 */     DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/*     */     
/*  86 */     int i = arrayOfDerValue.length;
/*  87 */     if (i < 1 || i > 2) {
/*  88 */       throw new IOException("Invalid encoding of EDIPartyName");
/*     */     }
/*  90 */     for (byte b = 0; b < i; b++) {
/*  91 */       DerValue derValue = arrayOfDerValue[b];
/*  92 */       if (derValue.isContextSpecific((byte)0) && 
/*  93 */         !derValue.isConstructed()) {
/*  94 */         if (this.assigner != null) {
/*  95 */           throw new IOException("Duplicate nameAssigner found in EDIPartyName");
/*     */         }
/*  97 */         derValue = derValue.data.getDerValue();
/*  98 */         this.assigner = derValue.getAsString();
/*     */       } 
/* 100 */       if (derValue.isContextSpecific((byte)1) && 
/* 101 */         !derValue.isConstructed()) {
/* 102 */         if (this.party != null) {
/* 103 */           throw new IOException("Duplicate partyName found in EDIPartyName");
/*     */         }
/* 105 */         derValue = derValue.data.getDerValue();
/* 106 */         this.party = derValue.getAsString();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 115 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 125 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 126 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 128 */     if (this.assigner != null) {
/* 129 */       DerOutputStream derOutputStream = new DerOutputStream();
/*     */       
/* 131 */       derOutputStream.putPrintableString(this.assigner);
/* 132 */       derOutputStream1.write(DerValue.createTag(-128, false, (byte)0), derOutputStream);
/*     */     } 
/*     */     
/* 135 */     if (this.party == null) {
/* 136 */       throw new IOException("Cannot have null partyName");
/*     */     }
/*     */     
/* 139 */     derOutputStream2.putPrintableString(this.party);
/* 140 */     derOutputStream1.write(DerValue.createTag(-128, false, (byte)1), derOutputStream2);
/*     */ 
/*     */     
/* 143 */     paramDerOutputStream.write((byte)48, derOutputStream1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssignerName() {
/* 152 */     return this.assigner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPartyName() {
/* 161 */     return this.party;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 172 */     if (!(paramObject instanceof EDIPartyName))
/* 173 */       return false; 
/* 174 */     String str1 = ((EDIPartyName)paramObject).assigner;
/* 175 */     if (this.assigner == null) {
/* 176 */       if (str1 != null) {
/* 177 */         return false;
/*     */       }
/* 179 */     } else if (!this.assigner.equals(str1)) {
/* 180 */       return false;
/*     */     } 
/* 182 */     String str2 = ((EDIPartyName)paramObject).party;
/* 183 */     if (this.party == null) {
/* 184 */       if (str2 != null) {
/* 185 */         return false;
/*     */       }
/* 187 */     } else if (!this.party.equals(str2)) {
/* 188 */       return false;
/*     */     } 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 199 */     if (this.myhash == -1) {
/* 200 */       this.myhash = 37 + ((this.party == null) ? 1 : this.party.hashCode());
/* 201 */       if (this.assigner != null) {
/* 202 */         this.myhash = 37 * this.myhash + this.assigner.hashCode();
/*     */       }
/*     */     } 
/* 205 */     return this.myhash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 212 */     return "EDIPartyName: " + ((this.assigner == null) ? "" : ("  nameAssigner = " + this.assigner + ",")) + "  partyName = " + this.party;
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
/*     */   public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException {
/*     */     byte b;
/* 235 */     if (paramGeneralNameInterface == null) {
/* 236 */       b = -1;
/* 237 */     } else if (paramGeneralNameInterface.getType() != 5) {
/* 238 */       b = -1;
/*     */     } else {
/* 240 */       throw new UnsupportedOperationException("Narrowing, widening, and matching of names not supported for EDIPartyName");
/*     */     } 
/* 242 */     return b;
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
/*     */   public int subtreeDepth() throws UnsupportedOperationException {
/* 254 */     throw new UnsupportedOperationException("subtreeDepth() not supported for EDIPartyName");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/EDIPartyName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */