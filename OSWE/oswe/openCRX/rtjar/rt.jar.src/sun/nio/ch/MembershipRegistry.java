/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MembershipRegistry
/*     */ {
/*  42 */   private Map<InetAddress, List<MembershipKeyImpl>> groups = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MembershipKey checkMembership(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) {
/*  54 */     if (this.groups != null) {
/*  55 */       List list = this.groups.get(paramInetAddress1);
/*  56 */       if (list != null)
/*  57 */         for (MembershipKeyImpl membershipKeyImpl : list) {
/*  58 */           if (membershipKeyImpl.networkInterface().equals(paramNetworkInterface)) {
/*     */ 
/*     */             
/*  61 */             if (paramInetAddress2 == null) {
/*  62 */               if (membershipKeyImpl.sourceAddress() == null)
/*  63 */                 return membershipKeyImpl; 
/*  64 */               throw new IllegalStateException("Already a member to receive all packets");
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*  69 */             if (membershipKeyImpl.sourceAddress() == null)
/*  70 */               throw new IllegalStateException("Already have source-specific membership"); 
/*  71 */             if (paramInetAddress2.equals(membershipKeyImpl.sourceAddress())) {
/*  72 */               return membershipKeyImpl;
/*     */             }
/*     */           } 
/*     */         }  
/*     */     } 
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void add(MembershipKeyImpl paramMembershipKeyImpl) {
/*     */     List<MembershipKeyImpl> list;
/*  84 */     InetAddress inetAddress = paramMembershipKeyImpl.group();
/*     */     
/*  86 */     if (this.groups == null) {
/*  87 */       this.groups = new HashMap<>();
/*  88 */       list = null;
/*     */     } else {
/*  90 */       list = this.groups.get(inetAddress);
/*     */     } 
/*  92 */     if (list == null) {
/*  93 */       list = new LinkedList();
/*  94 */       this.groups.put(inetAddress, list);
/*     */     } 
/*  96 */     list.add(paramMembershipKeyImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(MembershipKeyImpl paramMembershipKeyImpl) {
/* 103 */     InetAddress inetAddress = paramMembershipKeyImpl.group();
/* 104 */     List list = this.groups.get(inetAddress);
/* 105 */     if (list != null) {
/* 106 */       Iterator<MembershipKeyImpl> iterator = list.iterator();
/* 107 */       while (iterator.hasNext()) {
/* 108 */         if (iterator.next() == paramMembershipKeyImpl) {
/* 109 */           iterator.remove();
/*     */           break;
/*     */         } 
/*     */       } 
/* 113 */       if (list.isEmpty()) {
/* 114 */         this.groups.remove(inetAddress);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void invalidateAll() {
/* 123 */     if (this.groups != null)
/* 124 */       for (InetAddress inetAddress : this.groups.keySet()) {
/* 125 */         for (MembershipKeyImpl membershipKeyImpl : this.groups.get(inetAddress))
/* 126 */           membershipKeyImpl.invalidate(); 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/MembershipRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */