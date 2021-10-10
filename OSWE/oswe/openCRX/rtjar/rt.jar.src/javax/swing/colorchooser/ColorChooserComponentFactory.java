/*    */ package javax.swing.colorchooser;
/*    */ 
/*    */ import javax.swing.JComponent;
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
/*    */ public class ColorChooserComponentFactory
/*    */ {
/*    */   public static AbstractColorChooserPanel[] getDefaultChooserPanels() {
/* 51 */     return new AbstractColorChooserPanel[] { new DefaultSwatchChooserPanel(), new ColorChooserPanel(new ColorModelHSV()), new ColorChooserPanel(new ColorModelHSL()), new ColorChooserPanel(new ColorModel()), new ColorChooserPanel(new ColorModelCMYK()) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static JComponent getPreviewPanel() {
/* 61 */     return new DefaultPreviewPanel();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorChooserComponentFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */