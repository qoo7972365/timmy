/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.krb5.internal.util.KrbDataOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCacheOutputStream
/*     */   extends KrbDataOutputStream
/*     */   implements FileCCacheConstants
/*     */ {
/*     */   public CCacheOutputStream(OutputStream paramOutputStream) {
/*  47 */     super(paramOutputStream);
/*     */   }
/*     */   
/*     */   public void writeHeader(PrincipalName paramPrincipalName, int paramInt) throws IOException {
/*  51 */     write((paramInt & 0xFF00) >> 8);
/*  52 */     write(paramInt & 0xFF);
/*  53 */     paramPrincipalName.writePrincipal(this);
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
/*     */   public void addCreds(Credentials paramCredentials) throws IOException, Asn1Exception {
/*  69 */     paramCredentials.cname.writePrincipal(this);
/*  70 */     paramCredentials.sname.writePrincipal(this);
/*  71 */     paramCredentials.key.writeKey(this);
/*  72 */     write32((int)(paramCredentials.authtime.getTime() / 1000L));
/*  73 */     if (paramCredentials.starttime != null)
/*  74 */     { write32((int)(paramCredentials.starttime.getTime() / 1000L)); }
/*  75 */     else { write32(0); }
/*  76 */      write32((int)(paramCredentials.endtime.getTime() / 1000L));
/*  77 */     if (paramCredentials.renewTill != null) {
/*  78 */       write32((int)(paramCredentials.renewTill.getTime() / 1000L));
/*     */     } else {
/*  80 */       write32(0);
/*  81 */     }  if (paramCredentials.isEncInSKey) {
/*  82 */       write8(1);
/*     */     } else {
/*  84 */       write8(0);
/*  85 */     }  writeFlags(paramCredentials.flags);
/*  86 */     if (paramCredentials.caddr == null) {
/*  87 */       write32(0);
/*     */     } else {
/*  89 */       paramCredentials.caddr.writeAddrs(this);
/*     */     } 
/*  91 */     if (paramCredentials.authorizationData == null) {
/*  92 */       write32(0);
/*     */     } else {
/*     */       
/*  95 */       paramCredentials.authorizationData.writeAuth(this);
/*  96 */     }  writeTicket(paramCredentials.ticket);
/*  97 */     writeTicket(paramCredentials.secondTicket);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addConfigEntry(PrincipalName paramPrincipalName, CredentialsCache.ConfigEntry paramConfigEntry) throws IOException {
/* 102 */     paramPrincipalName.writePrincipal(this);
/* 103 */     paramConfigEntry.getSName().writePrincipal(this);
/* 104 */     write16(0); write16(0); write32(0);
/* 105 */     write32(0); write32(0); write32(0); write32(0);
/* 106 */     write8(0);
/* 107 */     write32(0);
/* 108 */     write32(0);
/* 109 */     write32(0);
/* 110 */     write32((paramConfigEntry.getData()).length);
/* 111 */     write(paramConfigEntry.getData());
/* 112 */     write32(0);
/*     */   }
/*     */   
/*     */   void writeTicket(Ticket paramTicket) throws IOException, Asn1Exception {
/* 116 */     if (paramTicket == null) {
/* 117 */       write32(0);
/*     */     } else {
/*     */       
/* 120 */       byte[] arrayOfByte = paramTicket.asn1Encode();
/* 121 */       write32(arrayOfByte.length);
/* 122 */       write(arrayOfByte, 0, arrayOfByte.length);
/*     */     } 
/*     */   }
/*     */   
/*     */   void writeFlags(TicketFlags paramTicketFlags) throws IOException {
/* 127 */     int i = 0;
/* 128 */     boolean[] arrayOfBoolean = paramTicketFlags.toBooleanArray();
/* 129 */     if (arrayOfBoolean[1] == true) {
/* 130 */       i |= 0x40000000;
/*     */     }
/* 132 */     if (arrayOfBoolean[2] == true) {
/* 133 */       i |= 0x20000000;
/*     */     }
/* 135 */     if (arrayOfBoolean[3] == true) {
/* 136 */       i |= 0x10000000;
/*     */     }
/* 138 */     if (arrayOfBoolean[4] == true) {
/* 139 */       i |= 0x8000000;
/*     */     }
/* 141 */     if (arrayOfBoolean[5] == true) {
/* 142 */       i |= 0x4000000;
/*     */     }
/* 144 */     if (arrayOfBoolean[6] == true) {
/* 145 */       i |= 0x2000000;
/*     */     }
/* 147 */     if (arrayOfBoolean[7] == true) {
/* 148 */       i |= 0x1000000;
/*     */     }
/* 150 */     if (arrayOfBoolean[8] == true) {
/* 151 */       i |= 0x800000;
/*     */     }
/* 153 */     if (arrayOfBoolean[9] == true) {
/* 154 */       i |= 0x400000;
/*     */     }
/* 156 */     if (arrayOfBoolean[10] == true) {
/* 157 */       i |= 0x200000;
/*     */     }
/* 159 */     if (arrayOfBoolean[11] == true) {
/* 160 */       i |= 0x100000;
/*     */     }
/* 162 */     write32(i);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ccache/CCacheOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */