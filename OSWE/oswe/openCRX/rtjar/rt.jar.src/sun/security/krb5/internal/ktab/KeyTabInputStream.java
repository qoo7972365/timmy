/*     */ package sun.security.krb5.internal.ktab;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.util.KrbDataInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyTabInputStream
/*     */   extends KrbDataInputStream
/*     */   implements KeyTabConstants
/*     */ {
/*  51 */   boolean DEBUG = Krb5.DEBUG;
/*     */   int index;
/*     */   
/*     */   public KeyTabInputStream(InputStream paramInputStream) {
/*  55 */     super(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int readEntryLength() throws IOException {
/*  61 */     return read(4);
/*     */   }
/*     */ 
/*     */   
/*     */   KeyTabEntry readEntry(int paramInt1, int paramInt2) throws IOException, RealmException {
/*  66 */     this.index = paramInt1;
/*  67 */     if (this.index == 0) {
/*  68 */       return null;
/*     */     }
/*  70 */     if (this.index < 0) {
/*  71 */       skip(Math.abs(this.index));
/*  72 */       return null;
/*     */     } 
/*  74 */     int i = read(2);
/*  75 */     this.index -= 2;
/*  76 */     if (paramInt2 == 1281) {
/*  77 */       i--;
/*     */     }
/*  79 */     Realm realm = new Realm(readName());
/*  80 */     String[] arrayOfString = new String[i]; int j;
/*  81 */     for (j = 0; j < i; j++) {
/*  82 */       arrayOfString[j] = readName();
/*     */     }
/*  84 */     j = read(4);
/*  85 */     this.index -= 4;
/*  86 */     PrincipalName principalName = new PrincipalName(j, arrayOfString, realm);
/*  87 */     KerberosTime kerberosTime = readTimeStamp();
/*     */     
/*  89 */     int k = read() & 0xFF;
/*  90 */     this.index--;
/*  91 */     int m = read(2);
/*  92 */     this.index -= 2;
/*  93 */     int n = read(2);
/*  94 */     this.index -= 2;
/*  95 */     byte[] arrayOfByte = readKey(n);
/*  96 */     this.index -= n;
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (this.index >= 4) {
/* 101 */       int i1 = read(4);
/* 102 */       if (i1 != 0) {
/* 103 */         k = i1;
/*     */       }
/* 105 */       this.index -= 4;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (this.index < 0) {
/* 110 */       throw new RealmException("Keytab is corrupted");
/*     */     }
/*     */ 
/*     */     
/* 114 */     skip(this.index);
/*     */     
/* 116 */     return new KeyTabEntry(principalName, realm, kerberosTime, k, m, arrayOfByte);
/*     */   }
/*     */   
/*     */   byte[] readKey(int paramInt) throws IOException {
/* 120 */     byte[] arrayOfByte = new byte[paramInt];
/* 121 */     read(arrayOfByte, 0, paramInt);
/* 122 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   KerberosTime readTimeStamp() throws IOException {
/* 126 */     this.index -= 4;
/* 127 */     return new KerberosTime(read(4) * 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   String readName() throws IOException {
/* 132 */     int i = read(2);
/* 133 */     this.index -= 2;
/* 134 */     byte[] arrayOfByte = new byte[i];
/* 135 */     read(arrayOfByte, 0, i);
/* 136 */     this.index -= i;
/* 137 */     String str = new String(arrayOfByte);
/* 138 */     if (this.DEBUG) {
/* 139 */       System.out.println(">>> KeyTabInputStream, readName(): " + str);
/*     */     }
/* 141 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ktab/KeyTabInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */