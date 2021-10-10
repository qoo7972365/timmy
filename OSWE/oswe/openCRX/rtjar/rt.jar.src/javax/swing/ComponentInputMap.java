/*     */ package javax.swing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentInputMap
/*     */   extends InputMap
/*     */ {
/*     */   private JComponent component;
/*     */   
/*     */   public ComponentInputMap(JComponent paramJComponent) {
/*  51 */     this.component = paramJComponent;
/*  52 */     if (paramJComponent == null) {
/*  53 */       throw new IllegalArgumentException("ComponentInputMaps must be associated with a non-null JComponent");
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
/*     */   public void setParent(InputMap paramInputMap) {
/*  69 */     if (getParent() == paramInputMap) {
/*     */       return;
/*     */     }
/*  72 */     if (paramInputMap != null && (!(paramInputMap instanceof ComponentInputMap) || ((ComponentInputMap)paramInputMap)
/*  73 */       .getComponent() != getComponent())) {
/*  74 */       throw new IllegalArgumentException("ComponentInputMaps must have a parent ComponentInputMap associated with the same component");
/*     */     }
/*  76 */     super.setParent(paramInputMap);
/*  77 */     getComponent().componentInputMapChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JComponent getComponent() {
/*  84 */     return this.component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(KeyStroke paramKeyStroke, Object paramObject) {
/*  93 */     super.put(paramKeyStroke, paramObject);
/*  94 */     if (getComponent() != null) {
/*  95 */       getComponent().componentInputMapChanged(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(KeyStroke paramKeyStroke) {
/* 103 */     super.remove(paramKeyStroke);
/* 104 */     if (getComponent() != null) {
/* 105 */       getComponent().componentInputMapChanged(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 113 */     int i = size();
/* 114 */     super.clear();
/* 115 */     if (i > 0 && getComponent() != null)
/* 116 */       getComponent().componentInputMapChanged(this); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ComponentInputMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */