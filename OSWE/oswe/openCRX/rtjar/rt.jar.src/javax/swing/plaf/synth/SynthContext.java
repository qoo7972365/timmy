/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthContext
/*     */ {
/*  43 */   private static final Queue<SynthContext> queue = new ConcurrentLinkedQueue<>();
/*     */   
/*     */   private JComponent component;
/*     */   private Region region;
/*     */   private SynthStyle style;
/*     */   private int state;
/*     */   
/*     */   static SynthContext getContext(JComponent paramJComponent, SynthStyle paramSynthStyle, int paramInt) {
/*  51 */     return getContext(paramJComponent, SynthLookAndFeel.getRegion(paramJComponent), paramSynthStyle, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static SynthContext getContext(JComponent paramJComponent, Region paramRegion, SynthStyle paramSynthStyle, int paramInt) {
/*  57 */     SynthContext synthContext = queue.poll();
/*  58 */     if (synthContext == null) {
/*  59 */       synthContext = new SynthContext();
/*     */     }
/*  61 */     synthContext.reset(paramJComponent, paramRegion, paramSynthStyle, paramInt);
/*  62 */     return synthContext;
/*     */   }
/*     */   
/*     */   static void releaseContext(SynthContext paramSynthContext) {
/*  66 */     queue.offer(paramSynthContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SynthContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext(JComponent paramJComponent, Region paramRegion, SynthStyle paramSynthStyle, int paramInt) {
/*  85 */     if (paramJComponent == null || paramRegion == null || paramSynthStyle == null) {
/*  86 */       throw new NullPointerException("You must supply a non-null component, region and style");
/*     */     }
/*     */     
/*  89 */     reset(paramJComponent, paramRegion, paramSynthStyle, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JComponent getComponent() {
/*  99 */     return this.component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Region getRegion() {
/* 108 */     return this.region;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSubregion() {
/* 115 */     return getRegion().isSubregion();
/*     */   }
/*     */   
/*     */   void setStyle(SynthStyle paramSynthStyle) {
/* 119 */     this.style = paramSynthStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthStyle getStyle() {
/* 128 */     return this.style;
/*     */   }
/*     */   
/*     */   void setComponentState(int paramInt) {
/* 132 */     this.state = paramInt;
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
/*     */   public int getComponentState() {
/* 146 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void reset(JComponent paramJComponent, Region paramRegion, SynthStyle paramSynthStyle, int paramInt) {
/* 154 */     this.component = paramJComponent;
/* 155 */     this.region = paramRegion;
/* 156 */     this.style = paramSynthStyle;
/* 157 */     this.state = paramInt;
/*     */   }
/*     */   
/*     */   void dispose() {
/* 161 */     this.component = null;
/* 162 */     this.style = null;
/* 163 */     releaseContext(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SynthPainter getPainter() {
/* 171 */     SynthPainter synthPainter = getStyle().getPainter(this);
/*     */     
/* 173 */     if (synthPainter != null) {
/* 174 */       return synthPainter;
/*     */     }
/* 176 */     return SynthPainter.NULL_PAINTER;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */