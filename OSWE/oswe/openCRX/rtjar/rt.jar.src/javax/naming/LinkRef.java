/*     */ package javax.naming;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinkRef
/*     */   extends Reference
/*     */ {
/*  77 */   static final String linkClassName = LinkRef.class.getName();
/*     */   
/*     */   static final String linkAddrType = "LinkAddress";
/*     */   
/*     */   private static final long serialVersionUID = -5386290613498931298L;
/*     */ 
/*     */   
/*     */   public LinkRef(Name paramName) {
/*  85 */     super(linkClassName, new StringRefAddr("LinkAddress", paramName.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkRef(String paramString) {
/*  93 */     super(linkClassName, new StringRefAddr("LinkAddress", paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLinkName() throws NamingException {
/* 104 */     if (this.className != null && this.className.equals(linkClassName)) {
/* 105 */       RefAddr refAddr = get("LinkAddress");
/* 106 */       if (refAddr != null && refAddr instanceof StringRefAddr) {
/* 107 */         return (String)((StringRefAddr)refAddr).getContent();
/*     */       }
/*     */     } 
/* 110 */     throw new MalformedLinkException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/LinkRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */