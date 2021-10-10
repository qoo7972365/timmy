/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.plaf.SeparatorUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSeparator
/*     */   extends JComponent
/*     */   implements SwingConstants, Accessible
/*     */ {
/*     */   private static final String uiClassID = "SeparatorUI";
/*  83 */   private int orientation = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public JSeparator() {
/*  88 */     this(0);
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
/*     */   public JSeparator(int paramInt) {
/* 104 */     checkOrientation(paramInt);
/* 105 */     this.orientation = paramInt;
/* 106 */     setFocusable(false);
/* 107 */     updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SeparatorUI getUI() {
/* 116 */     return (SeparatorUI)this.ui;
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
/*     */   public void setUI(SeparatorUI paramSeparatorUI) {
/* 131 */     setUI(paramSeparatorUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 140 */     setUI((SeparatorUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 152 */     return "SeparatorUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 162 */     paramObjectOutputStream.defaultWriteObject();
/* 163 */     if (getUIClassID().equals("SeparatorUI")) {
/* 164 */       byte b = JComponent.getWriteObjCounter(this);
/* 165 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 166 */       if (b == 0 && this.ui != null) {
/* 167 */         this.ui.installUI(this);
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
/*     */   
/*     */   public int getOrientation() {
/* 184 */     return this.orientation;
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
/*     */ 
/*     */   
/*     */   public void setOrientation(int paramInt) {
/* 207 */     if (this.orientation == paramInt) {
/*     */       return;
/*     */     }
/* 210 */     int i = this.orientation;
/* 211 */     checkOrientation(paramInt);
/* 212 */     this.orientation = paramInt;
/* 213 */     firePropertyChange("orientation", i, paramInt);
/* 214 */     revalidate();
/* 215 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkOrientation(int paramInt) {
/* 220 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */         return;
/*     */     } 
/*     */     
/* 226 */     throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
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
/*     */   protected String paramString() {
/* 242 */     String str = (this.orientation == 0) ? "HORIZONTAL" : "VERTICAL";
/*     */ 
/*     */     
/* 245 */     return super.paramString() + ",orientation=" + str;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 263 */     if (this.accessibleContext == null) {
/* 264 */       this.accessibleContext = new AccessibleJSeparator();
/*     */     }
/* 266 */     return this.accessibleContext;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleJSeparator
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 293 */       return AccessibleRole.SEPARATOR;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JSeparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */