/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.MouseInputAdapter;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalInternalFrameUI
/*     */   extends BasicInternalFrameUI
/*     */ {
/*  46 */   private static final PropertyChangeListener metalPropertyChangeListener = new MetalPropertyChangeHandler();
/*     */ 
/*     */   
/*  49 */   private static final Border handyEmptyBorder = new EmptyBorder(0, 0, 0, 0);
/*     */   
/*  51 */   protected static String IS_PALETTE = "JInternalFrame.isPalette";
/*  52 */   private static String IS_PALETTE_KEY = "JInternalFrame.isPalette";
/*  53 */   private static String FRAME_TYPE = "JInternalFrame.frameType";
/*  54 */   private static String NORMAL_FRAME = "normal";
/*  55 */   private static String PALETTE_FRAME = "palette";
/*  56 */   private static String OPTION_DIALOG = "optionDialog";
/*     */   
/*     */   public MetalInternalFrameUI(JInternalFrame paramJInternalFrame) {
/*  59 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new MetalInternalFrameUI((JInternalFrame)paramJComponent);
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  67 */     super.installUI(paramJComponent);
/*     */     
/*  69 */     Object object = paramJComponent.getClientProperty(IS_PALETTE_KEY);
/*  70 */     if (object != null) {
/*  71 */       setPalette(((Boolean)object).booleanValue());
/*     */     }
/*     */     
/*  74 */     Container container = this.frame.getContentPane();
/*  75 */     stripContentBorder(container);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  80 */     this.frame = (JInternalFrame)paramJComponent;
/*     */     
/*  82 */     Container container = ((JInternalFrame)paramJComponent).getContentPane();
/*  83 */     if (container instanceof JComponent) {
/*  84 */       JComponent jComponent = (JComponent)container;
/*  85 */       if (jComponent.getBorder() == handyEmptyBorder) {
/*  86 */         jComponent.setBorder((Border)null);
/*     */       }
/*     */     } 
/*  89 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  93 */     super.installListeners();
/*  94 */     this.frame.addPropertyChangeListener(metalPropertyChangeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/*  98 */     this.frame.removePropertyChangeListener(metalPropertyChangeListener);
/*  99 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 103 */     super.installKeyboardActions();
/* 104 */     ActionMap actionMap = SwingUtilities.getUIActionMap(this.frame);
/* 105 */     if (actionMap != null)
/*     */     {
/*     */       
/* 108 */       actionMap.remove("showSystemMenu");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 113 */     super.uninstallKeyboardActions();
/*     */   }
/*     */   
/*     */   protected void uninstallComponents() {
/* 117 */     this.titlePane = null;
/* 118 */     super.uninstallComponents();
/*     */   }
/*     */   
/*     */   private void stripContentBorder(Object paramObject) {
/* 122 */     if (paramObject instanceof JComponent) {
/* 123 */       JComponent jComponent = (JComponent)paramObject;
/* 124 */       Border border = jComponent.getBorder();
/* 125 */       if (border == null || border instanceof javax.swing.plaf.UIResource) {
/* 126 */         jComponent.setBorder(handyEmptyBorder);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JComponent createNorthPane(JInternalFrame paramJInternalFrame) {
/* 133 */     return new MetalInternalFrameTitlePane(paramJInternalFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFrameType(String paramString) {
/* 139 */     if (paramString.equals(OPTION_DIALOG)) {
/*     */       
/* 141 */       LookAndFeel.installBorder(this.frame, "InternalFrame.optionDialogBorder");
/* 142 */       ((MetalInternalFrameTitlePane)this.titlePane).setPalette(false);
/*     */     }
/* 144 */     else if (paramString.equals(PALETTE_FRAME)) {
/*     */       
/* 146 */       LookAndFeel.installBorder(this.frame, "InternalFrame.paletteBorder");
/* 147 */       ((MetalInternalFrameTitlePane)this.titlePane).setPalette(true);
/*     */     }
/*     */     else {
/*     */       
/* 151 */       LookAndFeel.installBorder(this.frame, "InternalFrame.border");
/* 152 */       ((MetalInternalFrameTitlePane)this.titlePane).setPalette(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPalette(boolean paramBoolean) {
/* 158 */     if (paramBoolean) {
/* 159 */       LookAndFeel.installBorder(this.frame, "InternalFrame.paletteBorder");
/*     */     } else {
/* 161 */       LookAndFeel.installBorder(this.frame, "InternalFrame.border");
/*     */     } 
/* 163 */     ((MetalInternalFrameTitlePane)this.titlePane).setPalette(paramBoolean);
/*     */   }
/*     */   
/*     */   private static class MetalPropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private MetalPropertyChangeHandler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 172 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 173 */       JInternalFrame jInternalFrame = (JInternalFrame)param1PropertyChangeEvent.getSource();
/*     */       
/* 175 */       if (!(jInternalFrame.getUI() instanceof MetalInternalFrameUI)) {
/*     */         return;
/*     */       }
/*     */       
/* 179 */       MetalInternalFrameUI metalInternalFrameUI = (MetalInternalFrameUI)jInternalFrame.getUI();
/*     */       
/* 181 */       if (str.equals(MetalInternalFrameUI.FRAME_TYPE)) {
/*     */         
/* 183 */         if (param1PropertyChangeEvent.getNewValue() instanceof String)
/*     */         {
/* 185 */           metalInternalFrameUI.setFrameType((String)param1PropertyChangeEvent.getNewValue());
/*     */         }
/*     */       }
/* 188 */       else if (str.equals(MetalInternalFrameUI.IS_PALETTE_KEY)) {
/*     */         
/* 190 */         if (param1PropertyChangeEvent.getNewValue() != null) {
/*     */           
/* 192 */           metalInternalFrameUI.setPalette(((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue());
/*     */         }
/*     */         else {
/*     */           
/* 196 */           metalInternalFrameUI.setPalette(false);
/*     */         } 
/* 198 */       } else if (str.equals("contentPane")) {
/* 199 */         metalInternalFrameUI.stripContentBorder(param1PropertyChangeEvent.getNewValue());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class BorderListener1
/*     */     extends BasicInternalFrameUI.BorderListener implements SwingConstants {
/*     */     private BorderListener1() {}
/*     */     
/*     */     Rectangle getIconBounds() {
/* 209 */       boolean bool = MetalUtils.isLeftToRight(MetalInternalFrameUI.this.frame);
/* 210 */       int i = bool ? 5 : (MetalInternalFrameUI.this.titlePane.getWidth() - 5);
/* 211 */       Rectangle rectangle = null;
/*     */       
/* 213 */       Icon icon = MetalInternalFrameUI.this.frame.getFrameIcon();
/* 214 */       if (icon != null) {
/* 215 */         if (!bool) {
/* 216 */           i -= icon.getIconWidth();
/*     */         }
/* 218 */         int j = MetalInternalFrameUI.this.titlePane.getHeight() / 2 - icon.getIconHeight() / 2;
/*     */         
/* 220 */         rectangle = new Rectangle(i, j, icon.getIconWidth(), icon.getIconHeight());
/*     */       } 
/* 222 */       return rectangle;
/*     */     }
/*     */     
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 226 */       if (param1MouseEvent.getClickCount() == 2 && param1MouseEvent.getSource() == MetalInternalFrameUI.this.getNorthPane() && MetalInternalFrameUI.this
/* 227 */         .frame.isClosable() && !MetalInternalFrameUI.this.frame.isIcon()) {
/* 228 */         Rectangle rectangle = getIconBounds();
/* 229 */         if (rectangle != null && rectangle.contains(param1MouseEvent.getX(), param1MouseEvent.getY())) {
/* 230 */           MetalInternalFrameUI.this.frame.doDefaultCloseAction();
/*     */         } else {
/*     */           
/* 233 */           super.mouseClicked(param1MouseEvent);
/*     */         } 
/*     */       } else {
/*     */         
/* 237 */         super.mouseClicked(param1MouseEvent);
/*     */       } 
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
/*     */   protected MouseInputAdapter createBorderListener(JInternalFrame paramJInternalFrame) {
/* 253 */     return new BorderListener1();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalInternalFrameUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */