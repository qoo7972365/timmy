/*     */ package sun.security.jgss.spnego;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NegTokenTarg
/*     */   extends SpNegoToken
/*     */ {
/*  57 */   private int negResult = 0;
/*  58 */   private Oid supportedMech = null;
/*  59 */   private byte[] responseToken = null;
/*  60 */   private byte[] mechListMIC = null;
/*     */ 
/*     */   
/*     */   NegTokenTarg(int paramInt, Oid paramOid, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*  64 */     super(1);
/*  65 */     this.negResult = paramInt;
/*  66 */     this.supportedMech = paramOid;
/*  67 */     this.responseToken = paramArrayOfbyte1;
/*  68 */     this.mechListMIC = paramArrayOfbyte2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NegTokenTarg(byte[] paramArrayOfbyte) throws GSSException {
/*  74 */     super(1);
/*  75 */     parseToken(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   final byte[] encode() throws GSSException {
/*     */     try {
/*  81 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */ 
/*     */       
/*  84 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/*  85 */       derOutputStream2.putEnumerated(this.negResult);
/*  86 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */ 
/*     */ 
/*     */       
/*  90 */       if (this.supportedMech != null) {
/*  91 */         DerOutputStream derOutputStream = new DerOutputStream();
/*  92 */         byte[] arrayOfByte = this.supportedMech.getDER();
/*  93 */         derOutputStream.write(arrayOfByte);
/*  94 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  99 */       if (this.responseToken != null) {
/* 100 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 101 */         derOutputStream.putOctetString(this.responseToken);
/* 102 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 107 */       if (this.mechListMIC != null) {
/* 108 */         if (DEBUG) {
/* 109 */           System.out.println("SpNegoToken NegTokenTarg: sending MechListMIC");
/*     */         }
/*     */         
/* 112 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 113 */         derOutputStream.putOctetString(this.mechListMIC);
/* 114 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), derOutputStream);
/*     */       }
/* 116 */       else if (GSSUtil.useMSInterop()) {
/*     */         
/* 118 */         if (this.responseToken != null) {
/* 119 */           if (DEBUG) {
/* 120 */             System.out.println("SpNegoToken NegTokenTarg: sending additional token for MS Interop");
/*     */           }
/*     */           
/* 123 */           DerOutputStream derOutputStream = new DerOutputStream();
/* 124 */           derOutputStream.putOctetString(this.responseToken);
/* 125 */           derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), derOutputStream);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 131 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/* 132 */       derOutputStream3.write((byte)48, derOutputStream1);
/*     */       
/* 134 */       return derOutputStream3.toByteArray();
/*     */     }
/* 136 */     catch (IOException iOException) {
/* 137 */       throw new GSSException(10, -1, "Invalid SPNEGO NegTokenTarg token : " + iOException
/* 138 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parseToken(byte[] paramArrayOfbyte) throws GSSException {
/*     */     try {
/* 144 */       DerValue derValue1 = new DerValue(paramArrayOfbyte);
/*     */       
/* 146 */       if (!derValue1.isContextSpecific((byte)1)) {
/* 147 */         throw new IOException("SPNEGO NegoTokenTarg : did not have the right token type");
/*     */       }
/*     */       
/* 150 */       DerValue derValue2 = derValue1.data.getDerValue();
/* 151 */       if (derValue2.tag != 48) {
/* 152 */         throw new IOException("SPNEGO NegoTokenTarg : did not have the Sequence tag");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 157 */       int i = -1;
/* 158 */       while (derValue2.data.available() > 0) {
/* 159 */         DerValue derValue = derValue2.data.getDerValue();
/* 160 */         if (derValue.isContextSpecific((byte)0)) {
/* 161 */           i = checkNextField(i, 0);
/* 162 */           this.negResult = derValue.data.getEnumerated();
/* 163 */           if (DEBUG)
/* 164 */             System.out.println("SpNegoToken NegTokenTarg: negotiated result = " + 
/* 165 */                 getNegoResultString(this.negResult));  continue;
/*     */         } 
/* 167 */         if (derValue.isContextSpecific((byte)1)) {
/* 168 */           i = checkNextField(i, 1);
/* 169 */           ObjectIdentifier objectIdentifier = derValue.data.getOID();
/* 170 */           this.supportedMech = new Oid(objectIdentifier.toString());
/* 171 */           if (DEBUG)
/* 172 */             System.out.println("SpNegoToken NegTokenTarg: supported mechanism = " + this.supportedMech); 
/*     */           continue;
/*     */         } 
/* 175 */         if (derValue.isContextSpecific((byte)2)) {
/* 176 */           i = checkNextField(i, 2);
/* 177 */           this.responseToken = derValue.data.getOctetString(); continue;
/* 178 */         }  if (derValue.isContextSpecific((byte)3)) {
/* 179 */           i = checkNextField(i, 3);
/* 180 */           if (!GSSUtil.useMSInterop()) {
/* 181 */             this.mechListMIC = derValue.data.getOctetString();
/* 182 */             if (DEBUG) {
/* 183 */               System.out.println("SpNegoToken NegTokenTarg: MechListMIC Token = " + 
/*     */                   
/* 185 */                   getHexBytes(this.mechListMIC));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 190 */     } catch (IOException iOException) {
/* 191 */       throw new GSSException(10, -1, "Invalid SPNEGO NegTokenTarg token : " + iOException
/* 192 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   int getNegotiatedResult() {
/* 197 */     return this.negResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getSupportedMech() {
/* 203 */     return this.supportedMech;
/*     */   }
/*     */   
/*     */   byte[] getResponseToken() {
/* 207 */     return this.responseToken;
/*     */   }
/*     */   
/*     */   byte[] getMechListMIC() {
/* 211 */     return this.mechListMIC;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/spnego/NegTokenTarg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */