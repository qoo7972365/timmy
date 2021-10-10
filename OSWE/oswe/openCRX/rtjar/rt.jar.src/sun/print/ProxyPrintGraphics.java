/*     */ package sun.print;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.PrintGraphics;
/*     */ import java.awt.PrintJob;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProxyPrintGraphics
/*     */   extends ProxyGraphics
/*     */   implements PrintGraphics
/*     */ {
/*     */   private PrintJob printJob;
/*     */   
/*     */   public ProxyPrintGraphics(Graphics paramGraphics, PrintJob paramPrintJob) {
/*  44 */     super(paramGraphics);
/*  45 */     this.printJob = paramPrintJob;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrintJob getPrintJob() {
/*  53 */     return this.printJob;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graphics create() {
/*  63 */     return new ProxyPrintGraphics(getGraphics().create(), this.printJob);
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
/*     */   public Graphics create(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  84 */     Graphics graphics = getGraphics().create(paramInt1, paramInt2, paramInt3, paramInt4);
/*  85 */     return new ProxyPrintGraphics(graphics, this.printJob);
/*     */   }
/*     */   
/*     */   public Graphics getGraphics() {
/*  89 */     return super.getGraphics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 100 */     super.dispose();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/ProxyPrintGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */