/*     */ package sun.security.acl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.acl.Group;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GroupImpl
/*     */   implements Group
/*     */ {
/*  37 */   private Vector<Principal> groupMembers = new Vector<>(50, 100);
/*     */ 
/*     */   
/*     */   private String group;
/*     */ 
/*     */ 
/*     */   
/*     */   public GroupImpl(String paramString) {
/*  45 */     this.group = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addMember(Principal paramPrincipal) {
/*  55 */     if (this.groupMembers.contains(paramPrincipal)) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     if (this.group.equals(paramPrincipal.toString())) {
/*  60 */       throw new IllegalArgumentException();
/*     */     }
/*  62 */     this.groupMembers.addElement(paramPrincipal);
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMember(Principal paramPrincipal) {
/*  73 */     return this.groupMembers.removeElement(paramPrincipal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<? extends Principal> members() {
/*  80 */     return this.groupMembers.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  89 */     if (this == paramObject) {
/*  90 */       return true;
/*     */     }
/*  92 */     if (!(paramObject instanceof Group)) {
/*  93 */       return false;
/*     */     }
/*  95 */     Group group = (Group)paramObject;
/*  96 */     return this.group.equals(group.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Group paramGroup) {
/* 101 */     return equals(paramGroup);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return this.group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return this.group.hashCode();
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
/*     */   public boolean isMember(Principal paramPrincipal) {
/* 131 */     if (this.groupMembers.contains(paramPrincipal)) {
/* 132 */       return true;
/*     */     }
/* 134 */     Vector<Group> vector = new Vector(10);
/* 135 */     return isMemberRecurse(paramPrincipal, vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return this.group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isMemberRecurse(Principal paramPrincipal, Vector<Group> paramVector) {
/* 153 */     Enumeration<? extends Principal> enumeration = members();
/* 154 */     while (enumeration.hasMoreElements()) {
/* 155 */       boolean bool = false;
/* 156 */       Principal principal = enumeration.nextElement();
/*     */ 
/*     */       
/* 159 */       if (principal.equals(paramPrincipal))
/* 160 */         return true; 
/* 161 */       if (principal instanceof GroupImpl) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         GroupImpl groupImpl = (GroupImpl)principal;
/* 172 */         paramVector.addElement(this);
/* 173 */         if (!paramVector.contains(groupImpl))
/* 174 */           bool = groupImpl.isMemberRecurse(paramPrincipal, paramVector); 
/* 175 */       } else if (principal instanceof Group) {
/* 176 */         Group group = (Group)principal;
/* 177 */         if (!paramVector.contains(group)) {
/* 178 */           bool = group.isMember(paramPrincipal);
/*     */         }
/*     */       } 
/* 181 */       if (bool)
/* 182 */         return bool; 
/*     */     } 
/* 184 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/acl/GroupImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */