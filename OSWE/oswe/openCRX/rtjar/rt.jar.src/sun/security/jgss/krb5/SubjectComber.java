/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.krb5.KerberosSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SubjectComber
/*     */ {
/*  51 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T find(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass) {
/*  63 */     return paramClass.cast(findAux(paramSubject, paramString1, paramString2, paramClass, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> List<T> findMany(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass) {
/*  71 */     return (List<T>)findAux(paramSubject, paramString1, paramString2, paramClass, false);
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
/*     */   private static <T> Object findAux(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass, boolean paramBoolean) {
/*  85 */     if (paramSubject == null) {
/*  86 */       return null;
/*     */     }
/*  88 */     ArrayList arrayList = paramBoolean ? null : new ArrayList();
/*     */     
/*  90 */     if (paramClass == KeyTab.class) {
/*     */       
/*  92 */       Iterator<KeyTab> iterator = paramSubject.<KeyTab>getPrivateCredentials(KeyTab.class).iterator();
/*  93 */       while (iterator.hasNext()) {
/*  94 */         KeyTab keyTab = iterator.next();
/*  95 */         if (paramString1 != null && keyTab.isBound()) {
/*  96 */           KerberosPrincipal kerberosPrincipal = keyTab.getPrincipal();
/*  97 */           if (kerberosPrincipal != null) {
/*  98 */             if (!paramString1.equals(kerberosPrincipal.getName())) {
/*     */               continue;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 104 */             boolean bool = false;
/*     */             
/* 106 */             for (KerberosPrincipal kerberosPrincipal1 : paramSubject.<KerberosPrincipal>getPrincipals(KerberosPrincipal.class)) {
/* 107 */               if (kerberosPrincipal1.getName().equals(paramString1)) {
/* 108 */                 bool = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 112 */             if (!bool)
/*     */               continue; 
/*     */           } 
/*     */         } 
/* 116 */         if (DEBUG) {
/* 117 */           System.out.println("Found " + paramClass.getSimpleName() + " " + keyTab);
/*     */         }
/*     */         
/* 120 */         if (paramBoolean) {
/* 121 */           return keyTab;
/*     */         }
/* 123 */         arrayList.add(paramClass.cast(keyTab));
/*     */       }
/*     */     
/* 126 */     } else if (paramClass == KerberosKey.class) {
/*     */ 
/*     */       
/* 129 */       Iterator<KerberosKey> iterator = paramSubject.<KerberosKey>getPrivateCredentials(KerberosKey.class).iterator();
/* 130 */       while (iterator.hasNext()) {
/* 131 */         KerberosKey kerberosKey = iterator.next();
/* 132 */         String str = kerberosKey.getPrincipal().getName();
/* 133 */         if (paramString1 == null || paramString1.equals(str)) {
/* 134 */           if (DEBUG) {
/* 135 */             System.out.println("Found " + paramClass
/* 136 */                 .getSimpleName() + " for " + str);
/*     */           }
/* 138 */           if (paramBoolean) {
/* 139 */             return kerberosKey;
/*     */           }
/* 141 */           arrayList.add(paramClass.cast(kerberosKey));
/*     */         }
/*     */       
/*     */       } 
/* 145 */     } else if (paramClass == KerberosTicket.class) {
/*     */ 
/*     */       
/* 148 */       Set set = paramSubject.getPrivateCredentials();
/* 149 */       synchronized (set) {
/* 150 */         Iterator<Object> iterator = set.iterator();
/* 151 */         while (iterator.hasNext()) {
/* 152 */           KerberosTicket kerberosTicket = (KerberosTicket)iterator.next();
/* 153 */           if (kerberosTicket instanceof KerberosTicket) {
/*     */             
/* 155 */             KerberosTicket kerberosTicket1 = kerberosTicket;
/* 156 */             if (DEBUG) {
/* 157 */               System.out.println("Found ticket for " + kerberosTicket1
/* 158 */                   .getClient() + " to go to " + kerberosTicket1
/*     */                   
/* 160 */                   .getServer() + " expiring on " + kerberosTicket1
/*     */                   
/* 162 */                   .getEndTime());
/*     */             }
/* 164 */             if (!kerberosTicket1.isCurrent()) {
/*     */ 
/*     */ 
/*     */               
/* 168 */               if (!paramSubject.isReadOnly()) {
/* 169 */                 iterator.remove();
/*     */                 try {
/* 171 */                   kerberosTicket1.destroy();
/* 172 */                   if (DEBUG) {
/* 173 */                     System.out.println("Removed and destroyed the expired Ticket \n" + kerberosTicket1);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/* 178 */                 catch (DestroyFailedException destroyFailedException) {
/* 179 */                   if (DEBUG) {
/* 180 */                     System.out.println("Expired ticket not detroyed successfully. " + destroyFailedException);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 189 */             KerberosPrincipal kerberosPrincipal = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetServerAlias(kerberosTicket1);
/* 190 */             if (paramString1 == null || kerberosTicket1
/* 191 */               .getServer().getName().equals(paramString1) || (kerberosPrincipal != null && paramString1
/*     */               
/* 193 */               .equals(kerberosPrincipal
/* 194 */                 .getName()))) {
/*     */ 
/*     */               
/* 197 */               KerberosPrincipal kerberosPrincipal1 = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetClientAlias(kerberosTicket1);
/* 198 */               if (paramString2 == null || paramString2
/* 199 */                 .equals(kerberosTicket1
/* 200 */                   .getClient().getName()) || (kerberosPrincipal1 != null && paramString2
/*     */                 
/* 202 */                 .equals(kerberosPrincipal1
/* 203 */                   .getName()))) {
/* 204 */                 if (paramBoolean) {
/* 205 */                   return kerberosTicket1;
/*     */                 }
/*     */ 
/*     */                 
/* 209 */                 if (paramString2 == null) {
/* 210 */                   if (kerberosPrincipal1 == null) {
/*     */                     
/* 212 */                     paramString2 = kerberosTicket1.getClient().getName();
/*     */                   } else {
/*     */                     
/* 215 */                     paramString2 = kerberosPrincipal1.getName();
/*     */                   } 
/*     */                 }
/* 218 */                 if (paramString1 == null) {
/* 219 */                   if (kerberosPrincipal == null) {
/*     */                     
/* 221 */                     paramString1 = kerberosTicket1.getServer().getName();
/*     */                   } else {
/*     */                     
/* 224 */                     paramString1 = kerberosPrincipal.getName();
/*     */                   } 
/*     */                 }
/* 227 */                 arrayList.add(paramClass.cast(kerberosTicket1));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 236 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/SubjectComber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */