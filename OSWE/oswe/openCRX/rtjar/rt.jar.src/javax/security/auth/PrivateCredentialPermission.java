/*     */ package javax.security.auth;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.Principal;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PrivateCredentialPermission
/*     */   extends Permission
/*     */ {
/*     */   private static final long serialVersionUID = 5284372143517237068L;
/* 108 */   private static final CredOwner[] EMPTY_PRINCIPALS = new CredOwner[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String credentialClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<Principal> principals;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient CredOwner[] credOwners;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean testing = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PrivateCredentialPermission(String paramString, Set<Principal> paramSet) {
/* 135 */     super(paramString);
/* 136 */     this.credentialClass = paramString;
/*     */     
/* 138 */     synchronized (paramSet) {
/* 139 */       if (paramSet.size() == 0) {
/* 140 */         this.credOwners = EMPTY_PRINCIPALS;
/*     */       } else {
/* 142 */         this.credOwners = new CredOwner[paramSet.size()];
/* 143 */         byte b = 0;
/* 144 */         Iterator<Principal> iterator = paramSet.iterator();
/* 145 */         while (iterator.hasNext()) {
/* 146 */           Principal principal = iterator.next();
/* 147 */           this.credOwners[b++] = new CredOwner(principal
/* 148 */               .getClass().getName(), principal
/* 149 */               .getName());
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public PrivateCredentialPermission(String paramString1, String paramString2) {
/* 171 */     super(paramString1);
/*     */     
/* 173 */     if (!"read".equalsIgnoreCase(paramString2))
/* 174 */       throw new IllegalArgumentException(
/* 175 */           ResourcesMgr.getString("actions.can.only.be.read.")); 
/* 176 */     init(paramString1);
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
/*     */   public String getCredentialClass() {
/* 189 */     return this.credentialClass;
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
/*     */   
/*     */   public String[][] getPrincipals() {
/* 212 */     if (this.credOwners == null || this.credOwners.length == 0) {
/* 213 */       return new String[0][0];
/*     */     }
/*     */     
/* 216 */     String[][] arrayOfString = new String[this.credOwners.length][2];
/* 217 */     for (byte b = 0; b < this.credOwners.length; b++) {
/* 218 */       arrayOfString[b][0] = (this.credOwners[b]).principalClass;
/* 219 */       arrayOfString[b][1] = (this.credOwners[b]).principalName;
/*     */     } 
/* 221 */     return arrayOfString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean implies(Permission paramPermission) {
/* 251 */     if (paramPermission == null || !(paramPermission instanceof PrivateCredentialPermission)) {
/* 252 */       return false;
/*     */     }
/* 254 */     PrivateCredentialPermission privateCredentialPermission = (PrivateCredentialPermission)paramPermission;
/*     */     
/* 256 */     if (!impliesCredentialClass(this.credentialClass, privateCredentialPermission.credentialClass)) {
/* 257 */       return false;
/*     */     }
/* 259 */     return impliesPrincipalSet(this.credOwners, privateCredentialPermission.credOwners);
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
/*     */   public boolean equals(Object paramObject) {
/* 280 */     if (paramObject == this) {
/* 281 */       return true;
/*     */     }
/* 283 */     if (!(paramObject instanceof PrivateCredentialPermission)) {
/* 284 */       return false;
/*     */     }
/* 286 */     PrivateCredentialPermission privateCredentialPermission = (PrivateCredentialPermission)paramObject;
/*     */     
/* 288 */     return (implies(privateCredentialPermission) && privateCredentialPermission.implies(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 297 */     return this.credentialClass.hashCode();
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
/*     */   public String getActions() {
/* 309 */     return "read";
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(String paramString) {
/* 328 */     if (paramString == null || paramString.trim().length() == 0) {
/* 329 */       throw new IllegalArgumentException("invalid empty name");
/*     */     }
/*     */     
/* 332 */     ArrayList<CredOwner> arrayList = new ArrayList();
/* 333 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, " ", true);
/* 334 */     String str1 = null;
/* 335 */     String str2 = null;
/*     */     
/* 337 */     if (this.testing) {
/* 338 */       System.out.println("whole name = " + paramString);
/*     */     }
/*     */     
/* 341 */     this.credentialClass = stringTokenizer.nextToken();
/* 342 */     if (this.testing) {
/* 343 */       System.out.println("Credential Class = " + this.credentialClass);
/*     */     }
/* 345 */     if (!stringTokenizer.hasMoreTokens()) {
/*     */       
/* 347 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid."));
/* 348 */       Object[] arrayOfObject = { paramString };
/* 349 */       throw new IllegalArgumentException(messageFormat
/* 350 */           .format(arrayOfObject) + 
/* 351 */           ResourcesMgr.getString("Credential.Class.not.followed.by.a.Principal.Class.and.Name"));
/*     */     } 
/*     */     
/* 354 */     while (stringTokenizer.hasMoreTokens()) {
/*     */ 
/*     */       
/* 357 */       stringTokenizer.nextToken();
/*     */ 
/*     */       
/* 360 */       str1 = stringTokenizer.nextToken();
/* 361 */       if (this.testing) {
/* 362 */         System.out.println("    Principal Class = " + str1);
/*     */       }
/* 364 */       if (!stringTokenizer.hasMoreTokens()) {
/*     */         
/* 366 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid."));
/* 367 */         Object[] arrayOfObject = { paramString };
/* 368 */         throw new IllegalArgumentException(messageFormat
/* 369 */             .format(arrayOfObject) + 
/* 370 */             ResourcesMgr.getString("Principal.Class.not.followed.by.a.Principal.Name"));
/*     */       } 
/*     */ 
/*     */       
/* 374 */       stringTokenizer.nextToken();
/*     */ 
/*     */       
/* 377 */       str2 = stringTokenizer.nextToken();
/*     */       
/* 379 */       if (!str2.startsWith("\"")) {
/*     */         
/* 381 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid."));
/* 382 */         Object[] arrayOfObject = { paramString };
/* 383 */         throw new IllegalArgumentException(messageFormat
/* 384 */             .format(arrayOfObject) + 
/* 385 */             ResourcesMgr.getString("Principal.Name.must.be.surrounded.by.quotes"));
/*     */       } 
/*     */       
/* 388 */       if (!str2.endsWith("\"")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 394 */         while (stringTokenizer.hasMoreTokens()) {
/* 395 */           str2 = str2 + stringTokenizer.nextToken();
/* 396 */           if (str2.endsWith("\"")) {
/*     */             break;
/*     */           }
/*     */         } 
/* 400 */         if (!str2.endsWith("\"")) {
/*     */ 
/*     */           
/* 403 */           MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid."));
/* 404 */           Object[] arrayOfObject = { paramString };
/* 405 */           throw new IllegalArgumentException(messageFormat
/* 406 */               .format(arrayOfObject) + 
/* 407 */               ResourcesMgr.getString("Principal.Name.missing.end.quote"));
/*     */         } 
/*     */       } 
/*     */       
/* 411 */       if (this.testing) {
/* 412 */         System.out.println("\tprincipalName = '" + str2 + "'");
/*     */       }
/*     */       
/* 415 */       str2 = str2.substring(1, str2.length() - 1);
/*     */       
/* 417 */       if (str1.equals("*") && 
/* 418 */         !str2.equals("*")) {
/* 419 */         throw new IllegalArgumentException(
/* 420 */             ResourcesMgr.getString("PrivateCredentialPermission.Principal.Class.can.not.be.a.wildcard.value.if.Principal.Name.is.not.a.wildcard.value"));
/*     */       }
/*     */       
/* 423 */       if (this.testing) {
/* 424 */         System.out.println("\tprincipalName = '" + str2 + "'");
/*     */       }
/* 426 */       arrayList.add(new CredOwner(str1, str2));
/*     */     } 
/*     */     
/* 429 */     this.credOwners = new CredOwner[arrayList.size()];
/* 430 */     arrayList.toArray(this.credOwners);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean impliesCredentialClass(String paramString1, String paramString2) {
/* 436 */     if (paramString1 == null || paramString2 == null) {
/* 437 */       return false;
/*     */     }
/* 439 */     if (this.testing) {
/* 440 */       System.out.println("credential class comparison: " + paramString1 + "/" + paramString2);
/*     */     }
/*     */     
/* 443 */     if (paramString1.equals("*")) {
/* 444 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     return paramString1.equals(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean impliesPrincipalSet(CredOwner[] paramArrayOfCredOwner1, CredOwner[] paramArrayOfCredOwner2) {
/* 463 */     if (paramArrayOfCredOwner1 == null || paramArrayOfCredOwner2 == null) {
/* 464 */       return false;
/*     */     }
/* 466 */     if (paramArrayOfCredOwner2.length == 0) {
/* 467 */       return true;
/*     */     }
/* 469 */     if (paramArrayOfCredOwner1.length == 0) {
/* 470 */       return false;
/*     */     }
/* 472 */     for (byte b = 0; b < paramArrayOfCredOwner1.length; b++) {
/* 473 */       boolean bool = false;
/* 474 */       for (byte b1 = 0; b1 < paramArrayOfCredOwner2.length; b1++) {
/* 475 */         if (paramArrayOfCredOwner1[b].implies(paramArrayOfCredOwner2[b1])) {
/* 476 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 480 */       if (!bool) {
/* 481 */         return false;
/*     */       }
/*     */     } 
/* 484 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 494 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */ 
/*     */     
/* 498 */     if (getName().indexOf(" ") == -1 && getName().indexOf("\"") == -1) {
/*     */ 
/*     */       
/* 501 */       this.credentialClass = getName();
/* 502 */       this.credOwners = EMPTY_PRINCIPALS;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 507 */       init(getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CredOwner
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -5607449830436408266L;
/*     */ 
/*     */     
/*     */     String principalClass;
/*     */ 
/*     */     
/*     */     String principalName;
/*     */ 
/*     */ 
/*     */     
/*     */     CredOwner(String param1String1, String param1String2) {
/* 528 */       this.principalClass = param1String1;
/* 529 */       this.principalName = param1String2;
/*     */     }
/*     */     
/*     */     public boolean implies(Object param1Object) {
/* 533 */       if (param1Object == null || !(param1Object instanceof CredOwner)) {
/* 534 */         return false;
/*     */       }
/* 536 */       CredOwner credOwner = (CredOwner)param1Object;
/*     */       
/* 538 */       if (this.principalClass.equals("*") || this.principalClass
/* 539 */         .equals(credOwner.principalClass))
/*     */       {
/* 541 */         if (this.principalName.equals("*") || this.principalName
/* 542 */           .equals(credOwner.principalName)) {
/* 543 */           return true;
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 551 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 556 */       MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("CredOwner.Principal.Class.class.Principal.Name.name"));
/* 557 */       Object[] arrayOfObject = { this.principalClass, this.principalName };
/* 558 */       return messageFormat.format(arrayOfObject);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/PrivateCredentialPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */