/*     */ package jdk.internal.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Handler
/*     */ {
/*     */   Label start;
/*     */   Label end;
/*     */   Label handler;
/*     */   String desc;
/*     */   int type;
/*     */   Handler next;
/*     */   
/*     */   static Handler remove(Handler paramHandler, Label paramLabel1, Label paramLabel2) {
/* 113 */     if (paramHandler == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     paramHandler.next = remove(paramHandler.next, paramLabel1, paramLabel2);
/*     */     
/* 118 */     int i = paramHandler.start.position;
/* 119 */     int j = paramHandler.end.position;
/* 120 */     int k = paramLabel1.position;
/* 121 */     int m = (paramLabel2 == null) ? Integer.MAX_VALUE : paramLabel2.position;
/*     */     
/* 123 */     if (k < j && m > i) {
/* 124 */       if (k <= i) {
/* 125 */         if (m >= j) {
/*     */           
/* 127 */           paramHandler = paramHandler.next;
/*     */         } else {
/*     */           
/* 130 */           paramHandler.start = paramLabel2;
/*     */         } 
/* 132 */       } else if (m >= j) {
/*     */         
/* 134 */         paramHandler.end = paramLabel1;
/*     */       } else {
/*     */         
/* 137 */         Handler handler = new Handler();
/* 138 */         handler.start = paramLabel2;
/* 139 */         handler.end = paramHandler.end;
/* 140 */         handler.handler = paramHandler.handler;
/* 141 */         handler.desc = paramHandler.desc;
/* 142 */         handler.type = paramHandler.type;
/* 143 */         handler.next = paramHandler.next;
/* 144 */         paramHandler.end = paramLabel1;
/* 145 */         paramHandler.next = handler;
/*     */       } 
/*     */     }
/* 148 */     return paramHandler;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */