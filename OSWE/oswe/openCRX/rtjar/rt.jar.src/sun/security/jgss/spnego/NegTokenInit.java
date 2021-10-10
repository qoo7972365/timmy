/*     */ package sun.security.jgss.spnego;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.util.BitArray;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NegTokenInit
/*     */   extends SpNegoToken
/*     */ {
/*  66 */   private byte[] mechTypes = null;
/*  67 */   private Oid[] mechTypeList = null;
/*     */   
/*  69 */   private BitArray reqFlags = null;
/*  70 */   private byte[] mechToken = null;
/*  71 */   private byte[] mechListMIC = null;
/*     */ 
/*     */ 
/*     */   
/*     */   NegTokenInit(byte[] paramArrayOfbyte1, BitArray paramBitArray, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/*  76 */     super(0);
/*  77 */     this.mechTypes = paramArrayOfbyte1;
/*  78 */     this.reqFlags = paramBitArray;
/*  79 */     this.mechToken = paramArrayOfbyte2;
/*  80 */     this.mechListMIC = paramArrayOfbyte3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NegTokenInit(byte[] paramArrayOfbyte) throws GSSException {
/*  86 */     super(0);
/*  87 */     parseToken(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   final byte[] encode() throws GSSException {
/*     */     try {
/*  93 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */ 
/*     */       
/*  96 */       if (this.mechTypes != null) {
/*  97 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), this.mechTypes);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 102 */       if (this.reqFlags != null) {
/* 103 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 104 */         derOutputStream.putUnalignedBitString(this.reqFlags);
/* 105 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (this.mechToken != null) {
/* 111 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 112 */         derOutputStream.putOctetString(this.mechToken);
/* 113 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (this.mechListMIC != null) {
/* 119 */         if (DEBUG) {
/* 120 */           System.out.println("SpNegoToken NegTokenInit: sending MechListMIC");
/*     */         }
/*     */         
/* 123 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 124 */         derOutputStream.putOctetString(this.mechListMIC);
/* 125 */         derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), derOutputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 130 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 131 */       derOutputStream2.write((byte)48, derOutputStream1);
/*     */       
/* 133 */       return derOutputStream2.toByteArray();
/*     */     }
/* 135 */     catch (IOException iOException) {
/* 136 */       throw new GSSException(10, -1, "Invalid SPNEGO NegTokenInit token : " + iOException
/* 137 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parseToken(byte[] paramArrayOfbyte) throws GSSException {
/*     */     try {
/* 143 */       DerValue derValue1 = new DerValue(paramArrayOfbyte);
/*     */       
/* 145 */       if (!derValue1.isContextSpecific((byte)0)) {
/* 146 */         throw new IOException("SPNEGO NegoTokenInit : did not have right token type");
/*     */       }
/*     */       
/* 149 */       DerValue derValue2 = derValue1.data.getDerValue();
/* 150 */       if (derValue2.tag != 48) {
/* 151 */         throw new IOException("SPNEGO NegoTokenInit : did not have the Sequence tag");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 156 */       int i = -1;
/* 157 */       while (derValue2.data.available() > 0) {
/* 158 */         DerValue derValue = derValue2.data.getDerValue();
/* 159 */         if (derValue.isContextSpecific((byte)0)) {
/*     */           
/* 161 */           i = checkNextField(i, 0);
/* 162 */           DerInputStream derInputStream = derValue.data;
/* 163 */           this.mechTypes = derInputStream.toByteArray();
/*     */ 
/*     */           
/* 166 */           DerValue[] arrayOfDerValue = derInputStream.getSequence(0);
/* 167 */           this.mechTypeList = new Oid[arrayOfDerValue.length];
/* 168 */           ObjectIdentifier objectIdentifier = null;
/* 169 */           for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 170 */             objectIdentifier = arrayOfDerValue[b].getOID();
/* 171 */             if (DEBUG) {
/* 172 */               System.out.println("SpNegoToken NegTokenInit: reading Mechanism Oid = " + objectIdentifier);
/*     */             }
/*     */             
/* 175 */             this.mechTypeList[b] = new Oid(objectIdentifier.toString());
/*     */           }  continue;
/* 177 */         }  if (derValue.isContextSpecific((byte)1)) {
/* 178 */           i = checkNextField(i, 1); continue;
/*     */         } 
/* 180 */         if (derValue.isContextSpecific((byte)2)) {
/* 181 */           i = checkNextField(i, 2);
/* 182 */           if (DEBUG) {
/* 183 */             System.out.println("SpNegoToken NegTokenInit: reading Mech Token");
/*     */           }
/*     */           
/* 186 */           this.mechToken = derValue.data.getOctetString(); continue;
/* 187 */         }  if (derValue.isContextSpecific((byte)3)) {
/* 188 */           i = checkNextField(i, 3);
/* 189 */           if (!GSSUtil.useMSInterop()) {
/* 190 */             this.mechListMIC = derValue.data.getOctetString();
/* 191 */             if (DEBUG) {
/* 192 */               System.out.println("SpNegoToken NegTokenInit: MechListMIC Token = " + 
/*     */                   
/* 194 */                   getHexBytes(this.mechListMIC));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 199 */     } catch (IOException iOException) {
/* 200 */       throw new GSSException(10, -1, "Invalid SPNEGO NegTokenInit token : " + iOException
/* 201 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   byte[] getMechTypes() {
/* 206 */     return this.mechTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid[] getMechTypeList() {
/* 212 */     return this.mechTypeList;
/*     */   }
/*     */   
/*     */   BitArray getReqFlags() {
/* 216 */     return this.reqFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMechToken() {
/* 222 */     return this.mechToken;
/*     */   }
/*     */   
/*     */   byte[] getMechListMIC() {
/* 226 */     return this.mechListMIC;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/spnego/NegTokenInit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */