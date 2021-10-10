/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import sun.security.util.BitArray;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReasonFlags
/*     */ {
/*     */   public static final String UNUSED = "unused";
/*     */   public static final String KEY_COMPROMISE = "key_compromise";
/*     */   public static final String CA_COMPROMISE = "ca_compromise";
/*     */   public static final String AFFILIATION_CHANGED = "affiliation_changed";
/*     */   public static final String SUPERSEDED = "superseded";
/*     */   public static final String CESSATION_OF_OPERATION = "cessation_of_operation";
/*     */   public static final String CERTIFICATE_HOLD = "certificate_hold";
/*     */   public static final String PRIVILEGE_WITHDRAWN = "privilege_withdrawn";
/*     */   public static final String AA_COMPROMISE = "aa_compromise";
/*  72 */   private static final String[] NAMES = new String[] { "unused", "key_compromise", "ca_compromise", "affiliation_changed", "superseded", "cessation_of_operation", "certificate_hold", "privilege_withdrawn", "aa_compromise" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] bitString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int name2Index(String paramString) throws IOException {
/*  85 */     for (byte b = 0; b < NAMES.length; b++) {
/*  86 */       if (NAMES[b].equalsIgnoreCase(paramString)) {
/*  87 */         return b;
/*     */       }
/*     */     } 
/*  90 */     throw new IOException("Name not recognized by ReasonFlags");
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
/*     */   private boolean isSet(int paramInt) {
/* 102 */     return (paramInt < this.bitString.length && this.bitString[paramInt]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(int paramInt, boolean paramBoolean) {
/* 111 */     if (paramInt >= this.bitString.length) {
/* 112 */       boolean[] arrayOfBoolean = new boolean[paramInt + 1];
/* 113 */       System.arraycopy(this.bitString, 0, arrayOfBoolean, 0, this.bitString.length);
/* 114 */       this.bitString = arrayOfBoolean;
/*     */     } 
/* 116 */     this.bitString[paramInt] = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReasonFlags(byte[] paramArrayOfbyte) {
/* 125 */     this.bitString = (new BitArray(paramArrayOfbyte.length * 8, paramArrayOfbyte)).toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReasonFlags(boolean[] paramArrayOfboolean) {
/* 134 */     this.bitString = paramArrayOfboolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReasonFlags(BitArray paramBitArray) {
/* 143 */     this.bitString = paramBitArray.toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReasonFlags(DerInputStream paramDerInputStream) throws IOException {
/* 153 */     DerValue derValue = paramDerInputStream.getDerValue();
/* 154 */     this.bitString = derValue.getUnalignedBitString(true).toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReasonFlags(DerValue paramDerValue) throws IOException {
/* 164 */     this.bitString = paramDerValue.getUnalignedBitString(true).toBooleanArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] getFlags() {
/* 171 */     return this.bitString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/* 178 */     if (!(paramObject instanceof Boolean)) {
/* 179 */       throw new IOException("Attribute must be of type Boolean.");
/*     */     }
/* 181 */     boolean bool = ((Boolean)paramObject).booleanValue();
/* 182 */     set(name2Index(paramString), bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 189 */     return Boolean.valueOf(isSet(name2Index(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 196 */     set(paramString, Boolean.FALSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 203 */     StringBuilder stringBuilder = new StringBuilder("Reason Flags [\n");
/*     */     
/* 205 */     if (isSet(0)) {
/* 206 */       stringBuilder.append("  Unused\n");
/*     */     }
/* 208 */     if (isSet(1)) {
/* 209 */       stringBuilder.append("  Key Compromise\n");
/*     */     }
/* 211 */     if (isSet(2)) {
/* 212 */       stringBuilder.append("  CA Compromise\n");
/*     */     }
/* 214 */     if (isSet(3)) {
/* 215 */       stringBuilder.append("  Affiliation_Changed\n");
/*     */     }
/* 217 */     if (isSet(4)) {
/* 218 */       stringBuilder.append("  Superseded\n");
/*     */     }
/* 220 */     if (isSet(5)) {
/* 221 */       stringBuilder.append("  Cessation Of Operation\n");
/*     */     }
/* 223 */     if (isSet(6)) {
/* 224 */       stringBuilder.append("  Certificate Hold\n");
/*     */     }
/* 226 */     if (isSet(7)) {
/* 227 */       stringBuilder.append("  Privilege Withdrawn\n");
/*     */     }
/* 229 */     if (isSet(8)) {
/* 230 */       stringBuilder.append("  AA Compromise\n");
/*     */     }
/* 232 */     stringBuilder.append("]\n");
/*     */     
/* 234 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 244 */     paramDerOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 252 */     AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
/* 253 */     for (byte b = 0; b < NAMES.length; b++) {
/* 254 */       attributeNameEnumeration.addElement(NAMES[b]);
/*     */     }
/* 256 */     return attributeNameEnumeration.elements();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/ReasonFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */