/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCRep;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Credentials
/*     */ {
/*     */   PrincipalName cname;
/*     */   PrincipalName sname;
/*     */   EncryptionKey key;
/*     */   KerberosTime authtime;
/*     */   KerberosTime starttime;
/*     */   KerberosTime endtime;
/*     */   KerberosTime renewTill;
/*     */   HostAddresses caddr;
/*     */   AuthorizationData authorizationData;
/*     */   public boolean isEncInSKey;
/*     */   TicketFlags flags;
/*     */   Ticket ticket;
/*     */   Ticket secondTicket;
/*  51 */   private boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials(PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, EncryptionKey paramEncryptionKey, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, boolean paramBoolean, TicketFlags paramTicketFlags, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket paramTicket1, Ticket paramTicket2) {
/*  67 */     this.cname = (PrincipalName)paramPrincipalName1.clone();
/*  68 */     this.sname = (PrincipalName)paramPrincipalName2.clone();
/*  69 */     this.key = (EncryptionKey)paramEncryptionKey.clone();
/*     */     
/*  71 */     this.authtime = paramKerberosTime1;
/*  72 */     this.starttime = paramKerberosTime2;
/*  73 */     this.endtime = paramKerberosTime3;
/*  74 */     this.renewTill = paramKerberosTime4;
/*     */     
/*  76 */     if (paramHostAddresses != null) {
/*  77 */       this.caddr = (HostAddresses)paramHostAddresses.clone();
/*     */     }
/*  79 */     if (paramAuthorizationData != null) {
/*  80 */       this.authorizationData = (AuthorizationData)paramAuthorizationData.clone();
/*     */     }
/*     */     
/*  83 */     this.isEncInSKey = paramBoolean;
/*  84 */     this.flags = (TicketFlags)paramTicketFlags.clone();
/*  85 */     this.ticket = (Ticket)paramTicket1.clone();
/*  86 */     if (paramTicket2 != null) {
/*  87 */       this.secondTicket = (Ticket)paramTicket2.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials(KDCRep paramKDCRep, Ticket paramTicket, AuthorizationData paramAuthorizationData, boolean paramBoolean) {
/*  96 */     if (paramKDCRep.encKDCRepPart == null) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     this.cname = (PrincipalName)paramKDCRep.cname.clone();
/* 101 */     this.ticket = (Ticket)paramKDCRep.ticket.clone();
/* 102 */     this.key = (EncryptionKey)paramKDCRep.encKDCRepPart.key.clone();
/* 103 */     this.flags = (TicketFlags)paramKDCRep.encKDCRepPart.flags.clone();
/* 104 */     this.authtime = paramKDCRep.encKDCRepPart.authtime;
/* 105 */     this.starttime = paramKDCRep.encKDCRepPart.starttime;
/* 106 */     this.endtime = paramKDCRep.encKDCRepPart.endtime;
/* 107 */     this.renewTill = paramKDCRep.encKDCRepPart.renewTill;
/*     */     
/* 109 */     this.sname = (PrincipalName)paramKDCRep.encKDCRepPart.sname.clone();
/* 110 */     this.caddr = (HostAddresses)paramKDCRep.encKDCRepPart.caddr.clone();
/* 111 */     this.secondTicket = (Ticket)paramTicket.clone();
/* 112 */     this
/* 113 */       .authorizationData = (AuthorizationData)paramAuthorizationData.clone();
/* 114 */     this.isEncInSKey = paramBoolean;
/*     */   }
/*     */   
/*     */   public Credentials(KDCRep paramKDCRep) {
/* 118 */     this(paramKDCRep, null);
/*     */   }
/*     */   
/*     */   public Credentials(KDCRep paramKDCRep, Ticket paramTicket) {
/* 122 */     this.sname = (PrincipalName)paramKDCRep.encKDCRepPart.sname.clone();
/* 123 */     this.cname = (PrincipalName)paramKDCRep.cname.clone();
/* 124 */     this.key = (EncryptionKey)paramKDCRep.encKDCRepPart.key.clone();
/* 125 */     this.authtime = paramKDCRep.encKDCRepPart.authtime;
/* 126 */     this.starttime = paramKDCRep.encKDCRepPart.starttime;
/* 127 */     this.endtime = paramKDCRep.encKDCRepPart.endtime;
/* 128 */     this.renewTill = paramKDCRep.encKDCRepPart.renewTill;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.flags = paramKDCRep.encKDCRepPart.flags;
/* 134 */     if (paramKDCRep.encKDCRepPart.caddr != null) {
/* 135 */       this.caddr = (HostAddresses)paramKDCRep.encKDCRepPart.caddr.clone();
/*     */     } else {
/* 137 */       this.caddr = null;
/*     */     } 
/* 139 */     this.ticket = (Ticket)paramKDCRep.ticket.clone();
/* 140 */     if (paramTicket != null) {
/* 141 */       this.secondTicket = (Ticket)paramTicket.clone();
/* 142 */       this.isEncInSKey = true;
/*     */     } else {
/* 144 */       this.secondTicket = null;
/* 145 */       this.isEncInSKey = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 153 */     boolean bool = true;
/* 154 */     if (this.endtime.getTime() < System.currentTimeMillis()) {
/* 155 */       bool = false;
/* 156 */     } else if (this.starttime != null) {
/* 157 */       if (this.starttime.getTime() > System.currentTimeMillis()) {
/* 158 */         bool = false;
/*     */       }
/*     */     }
/* 161 */     else if (this.authtime.getTime() > System.currentTimeMillis()) {
/* 162 */       bool = false;
/*     */     } 
/*     */     
/* 165 */     return bool;
/*     */   }
/*     */   
/*     */   public PrincipalName getServicePrincipal() throws RealmException {
/* 169 */     return this.sname;
/*     */   }
/*     */   
/*     */   public Ticket getTicket() throws RealmException {
/* 173 */     return this.ticket;
/*     */   }
/*     */   
/*     */   public PrincipalName getServicePrincipal2() throws RealmException {
/* 177 */     return (this.secondTicket == null) ? null : this.secondTicket.sname;
/*     */   }
/*     */   
/*     */   public PrincipalName getClientPrincipal() throws RealmException {
/* 181 */     return this.cname;
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
/*     */   public sun.security.krb5.Credentials setKrbCreds() {
/* 195 */     return new sun.security.krb5.Credentials(this.ticket, this.cname, null, this.sname, null, this.key, this.flags, this.authtime, this.starttime, this.endtime, this.renewTill, this.caddr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosTime getStartTime() {
/* 201 */     return this.starttime;
/*     */   }
/*     */   
/*     */   public KerberosTime getAuthTime() {
/* 205 */     return this.authtime;
/*     */   }
/*     */   
/*     */   public KerberosTime getEndTime() {
/* 209 */     return this.endtime;
/*     */   }
/*     */   
/*     */   public KerberosTime getRenewTill() {
/* 213 */     return this.renewTill;
/*     */   }
/*     */   
/*     */   public TicketFlags getTicketFlags() {
/* 217 */     return this.flags;
/*     */   }
/*     */   
/*     */   public int getEType() {
/* 221 */     return this.key.getEType();
/*     */   }
/*     */   
/*     */   public EncryptionKey getKey() {
/* 225 */     return this.key;
/*     */   }
/*     */   
/*     */   public int getTktEType() {
/* 229 */     return this.ticket.encPart.getEType();
/*     */   }
/*     */   
/*     */   public int getTktEType2() {
/* 233 */     return (this.secondTicket == null) ? 0 : this.secondTicket.encPart.getEType();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ccache/Credentials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */