/*     */ package sun.awt.geom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ChainEnd
/*     */ {
/*     */   CurveLink head;
/*     */   CurveLink tail;
/*     */   ChainEnd partner;
/*     */   int etag;
/*     */   
/*     */   public ChainEnd(CurveLink paramCurveLink, ChainEnd paramChainEnd) {
/*  35 */     this.head = paramCurveLink;
/*  36 */     this.tail = paramCurveLink;
/*  37 */     this.partner = paramChainEnd;
/*  38 */     this.etag = paramCurveLink.getEdgeTag();
/*     */   }
/*     */   
/*     */   public CurveLink getChain() {
/*  42 */     return this.head;
/*     */   }
/*     */   
/*     */   public void setOtherEnd(ChainEnd paramChainEnd) {
/*  46 */     this.partner = paramChainEnd;
/*     */   }
/*     */   
/*     */   public ChainEnd getPartner() {
/*  50 */     return this.partner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CurveLink linkTo(ChainEnd paramChainEnd) {
/*     */     ChainEnd chainEnd1, chainEnd2;
/*  58 */     if (this.etag == 0 || paramChainEnd.etag == 0)
/*     */     {
/*     */       
/*  61 */       throw new InternalError("ChainEnd linked more than once!");
/*     */     }
/*  63 */     if (this.etag == paramChainEnd.etag) {
/*  64 */       throw new InternalError("Linking chains of the same type!");
/*     */     }
/*     */ 
/*     */     
/*  68 */     if (this.etag == 1) {
/*  69 */       chainEnd1 = this;
/*  70 */       chainEnd2 = paramChainEnd;
/*     */     } else {
/*  72 */       chainEnd1 = paramChainEnd;
/*  73 */       chainEnd2 = this;
/*     */     } 
/*     */     
/*  76 */     this.etag = 0;
/*  77 */     paramChainEnd.etag = 0;
/*     */     
/*  79 */     chainEnd1.tail.setNext(chainEnd2.head);
/*  80 */     chainEnd1.tail = chainEnd2.tail;
/*  81 */     if (this.partner == paramChainEnd)
/*     */     {
/*  83 */       return chainEnd1.head;
/*     */     }
/*     */     
/*  86 */     ChainEnd chainEnd3 = chainEnd2.partner;
/*  87 */     ChainEnd chainEnd4 = chainEnd1.partner;
/*  88 */     chainEnd3.partner = chainEnd4;
/*  89 */     chainEnd4.partner = chainEnd3;
/*  90 */     if (chainEnd1.head.getYTop() < chainEnd3.head.getYTop()) {
/*  91 */       chainEnd1.tail.setNext(chainEnd3.head);
/*  92 */       chainEnd3.head = chainEnd1.head;
/*     */     } else {
/*  94 */       chainEnd4.tail.setNext(chainEnd1.head);
/*  95 */       chainEnd4.tail = chainEnd1.tail;
/*     */     } 
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public void addLink(CurveLink paramCurveLink) {
/* 101 */     if (this.etag == 1) {
/* 102 */       this.tail.setNext(paramCurveLink);
/* 103 */       this.tail = paramCurveLink;
/*     */     } else {
/* 105 */       paramCurveLink.setNext(this.head);
/* 106 */       this.head = paramCurveLink;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getX() {
/* 111 */     if (this.etag == 1) {
/* 112 */       return this.tail.getXBot();
/*     */     }
/* 114 */     return this.head.getXBot();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/geom/ChainEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */