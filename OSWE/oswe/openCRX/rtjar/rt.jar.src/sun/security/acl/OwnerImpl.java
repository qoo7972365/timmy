/*     */ package sun.security.acl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.acl.Group;
/*     */ import java.security.acl.LastOwnerException;
/*     */ import java.security.acl.NotOwnerException;
/*     */ import java.security.acl.Owner;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OwnerImpl
/*     */   implements Owner
/*     */ {
/*     */   private Group ownerGroup;
/*     */   
/*     */   public OwnerImpl(Principal paramPrincipal) {
/*  42 */     this.ownerGroup = new GroupImpl("AclOwners");
/*  43 */     this.ownerGroup.addMember(paramPrincipal);
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
/*     */   public synchronized boolean addOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException {
/*  61 */     if (!isOwner(paramPrincipal1)) {
/*  62 */       throw new NotOwnerException();
/*     */     }
/*  64 */     this.ownerGroup.addMember(paramPrincipal2);
/*  65 */     return false;
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
/*     */   public synchronized boolean deleteOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException, LastOwnerException {
/*  85 */     if (!isOwner(paramPrincipal1)) {
/*  86 */       throw new NotOwnerException();
/*     */     }
/*  88 */     Enumeration<Object> enumeration = this.ownerGroup.members();
/*     */ 
/*     */ 
/*     */     
/*  92 */     Object object = enumeration.nextElement();
/*  93 */     if (enumeration.hasMoreElements()) {
/*  94 */       return this.ownerGroup.removeMember(paramPrincipal2);
/*     */     }
/*  96 */     throw new LastOwnerException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isOwner(Principal paramPrincipal) {
/* 106 */     return this.ownerGroup.isMember(paramPrincipal);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/acl/OwnerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */