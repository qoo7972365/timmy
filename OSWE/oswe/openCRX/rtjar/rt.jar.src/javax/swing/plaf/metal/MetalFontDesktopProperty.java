/*    */ package javax.swing.plaf.metal;
/*    */ 
/*    */ import com.sun.java.swing.plaf.windows.DesktopProperty;
/*    */ import java.awt.Font;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MetalFontDesktopProperty
/*    */   extends DesktopProperty
/*    */ {
/* 39 */   private static final String[] propertyMapping = new String[] { "win.ansiVar.font.height", "win.tooltip.font.height", "win.ansiVar.font.height", "win.menu.font.height", "win.frame.captionFont.height", "win.menu.font.height" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   MetalFontDesktopProperty(int paramInt) {
/* 61 */     this(propertyMapping[paramInt], paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   MetalFontDesktopProperty(String paramString, int paramInt) {
/* 74 */     super(paramString, null);
/* 75 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object configureValue(Object paramObject) {
/* 83 */     if (paramObject instanceof Integer)
/*    */     {
/*    */       
/* 86 */       paramObject = new Font(DefaultMetalTheme.getDefaultFontName(this.type), DefaultMetalTheme.getDefaultFontStyle(this.type), ((Integer)paramObject).intValue());
/*    */     }
/* 88 */     return super.configureValue(paramObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object getDefaultValue() {
/* 95 */     return new Font(DefaultMetalTheme.getDefaultFontName(this.type), 
/* 96 */         DefaultMetalTheme.getDefaultFontStyle(this.type), 
/* 97 */         DefaultMetalTheme.getDefaultFontSize(this.type));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalFontDesktopProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */