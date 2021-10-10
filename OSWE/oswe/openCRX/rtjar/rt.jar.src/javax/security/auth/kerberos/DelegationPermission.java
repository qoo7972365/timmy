/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.BasicPermission;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DelegationPermission
/*     */   extends BasicPermission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 883133252142523922L;
/*     */   private transient String subordinate;
/*     */   private transient String service;
/*     */   
/*     */   public DelegationPermission(String paramString) {
/*  85 */     super(paramString);
/*  86 */     init(paramString);
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
/*     */   public DelegationPermission(String paramString1, String paramString2) {
/* 102 */     super(paramString1, paramString2);
/* 103 */     init(paramString1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String paramString) {
/* 112 */     StringTokenizer stringTokenizer = null;
/* 113 */     if (!paramString.startsWith("\"")) {
/* 114 */       throw new IllegalArgumentException("service principal [" + paramString + "] syntax invalid: improperly quoted");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     stringTokenizer = new StringTokenizer(paramString, "\"", false);
/* 120 */     this.subordinate = stringTokenizer.nextToken();
/* 121 */     if (stringTokenizer.countTokens() == 2) {
/* 122 */       stringTokenizer.nextToken();
/* 123 */       this.service = stringTokenizer.nextToken();
/* 124 */     } else if (stringTokenizer.countTokens() > 0) {
/* 125 */       throw new IllegalArgumentException("service principal [" + stringTokenizer
/* 126 */           .nextToken() + "] syntax invalid: improperly quoted");
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
/*     */   public boolean implies(Permission paramPermission) {
/* 144 */     if (!(paramPermission instanceof DelegationPermission)) {
/* 145 */       return false;
/*     */     }
/* 147 */     DelegationPermission delegationPermission = (DelegationPermission)paramPermission;
/* 148 */     if (this.subordinate.equals(delegationPermission.subordinate) && this.service
/* 149 */       .equals(delegationPermission.service)) {
/* 150 */       return true;
/*     */     }
/* 152 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 166 */     if (paramObject == this) {
/* 167 */       return true;
/*     */     }
/* 169 */     if (!(paramObject instanceof DelegationPermission)) {
/* 170 */       return false;
/*     */     }
/* 172 */     DelegationPermission delegationPermission = (DelegationPermission)paramObject;
/* 173 */     return implies(delegationPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 182 */     return getName().hashCode();
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 200 */     return new KrbDelegationPermissionCollection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 211 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 222 */     paramObjectInputStream.defaultReadObject();
/* 223 */     init(getName());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/DelegationPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */