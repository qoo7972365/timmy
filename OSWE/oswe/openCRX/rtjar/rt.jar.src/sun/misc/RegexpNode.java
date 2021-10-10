/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RegexpNode
/*     */ {
/*     */   char c;
/*     */   RegexpNode firstchild;
/*     */   RegexpNode nextsibling;
/*     */   int depth;
/*     */   boolean exact;
/*     */   Object result;
/* 268 */   String re = null;
/*     */   
/*     */   RegexpNode() {
/* 271 */     this.c = '#';
/* 272 */     this.depth = 0;
/*     */   }
/*     */   RegexpNode(char paramChar, int paramInt) {
/* 275 */     this.c = paramChar;
/* 276 */     this.depth = paramInt;
/*     */   }
/*     */   RegexpNode add(char paramChar) {
/* 279 */     RegexpNode regexpNode = this.firstchild;
/* 280 */     if (regexpNode == null) {
/* 281 */       regexpNode = new RegexpNode(paramChar, this.depth + 1);
/*     */     } else {
/* 283 */       while (regexpNode != null) {
/* 284 */         if (regexpNode.c == paramChar) {
/* 285 */           return regexpNode;
/*     */         }
/* 287 */         regexpNode = regexpNode.nextsibling;
/* 288 */       }  regexpNode = new RegexpNode(paramChar, this.depth + 1);
/* 289 */       regexpNode.nextsibling = this.firstchild;
/*     */     } 
/* 291 */     this.firstchild = regexpNode;
/* 292 */     return regexpNode;
/*     */   }
/*     */   RegexpNode find(char paramChar) {
/* 295 */     RegexpNode regexpNode = this.firstchild;
/* 296 */     for (; regexpNode != null; 
/* 297 */       regexpNode = regexpNode.nextsibling) {
/* 298 */       if (regexpNode.c == paramChar)
/* 299 */         return regexpNode; 
/* 300 */     }  return null;
/*     */   }
/*     */   void print(PrintStream paramPrintStream) {
/* 303 */     if (this.nextsibling != null) {
/* 304 */       RegexpNode regexpNode = this;
/* 305 */       paramPrintStream.print("(");
/* 306 */       while (regexpNode != null) {
/* 307 */         paramPrintStream.write(regexpNode.c);
/* 308 */         if (regexpNode.firstchild != null)
/* 309 */           regexpNode.firstchild.print(paramPrintStream); 
/* 310 */         regexpNode = regexpNode.nextsibling;
/* 311 */         paramPrintStream.write((regexpNode != null) ? 124 : 41);
/*     */       } 
/*     */     } else {
/* 314 */       paramPrintStream.write(this.c);
/* 315 */       if (this.firstchild != null)
/* 316 */         this.firstchild.print(paramPrintStream); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/RegexpNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */