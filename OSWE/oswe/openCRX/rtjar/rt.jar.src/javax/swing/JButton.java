/*     */ package javax.swing;
/*     */ 
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JButton
/*     */   extends AbstractButton
/*     */   implements Accessible
/*     */ {
/*     */   private static final String uiClassID = "ButtonUI";
/*     */   
/*     */   public JButton() {
/*  91 */     this((String)null, (Icon)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton(Icon paramIcon) {
/* 100 */     this((String)null, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"text"})
/*     */   public JButton(String paramString) {
/* 110 */     this(paramString, (Icon)null);
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
/*     */   public JButton(Action paramAction) {
/* 122 */     this();
/* 123 */     setAction(paramAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton(String paramString, Icon paramIcon) {
/* 134 */     setModel(new DefaultButtonModel());
/*     */ 
/*     */     
/* 137 */     init(paramString, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 147 */     setUI((ButtonUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 163 */     return "ButtonUI";
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
/*     */   public boolean isDefaultButton() {
/* 182 */     JRootPane jRootPane = SwingUtilities.getRootPane(this);
/* 183 */     if (jRootPane != null) {
/* 184 */       return (jRootPane.getDefaultButton() == this);
/*     */     }
/* 186 */     return false;
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
/*     */   public boolean isDefaultCapable() {
/* 198 */     return this.defaultCapable;
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
/*     */   public void setDefaultCapable(boolean paramBoolean) {
/* 219 */     boolean bool = this.defaultCapable;
/* 220 */     this.defaultCapable = paramBoolean;
/* 221 */     firePropertyChange("defaultCapable", bool, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 232 */     JRootPane jRootPane = SwingUtilities.getRootPane(this);
/* 233 */     if (jRootPane != null && jRootPane.getDefaultButton() == this) {
/* 234 */       jRootPane.setDefaultButton(null);
/*     */     }
/* 236 */     super.removeNotify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 244 */     paramObjectOutputStream.defaultWriteObject();
/* 245 */     if (getUIClassID().equals("ButtonUI")) {
/* 246 */       byte b = JComponent.getWriteObjCounter(this);
/* 247 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 248 */       if (b == 0 && this.ui != null) {
/* 249 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 265 */     String str = this.defaultCapable ? "true" : "false";
/*     */     
/* 267 */     return super.paramString() + ",defaultCapable=" + str;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 290 */     if (this.accessibleContext == null) {
/* 291 */       this.accessibleContext = new AccessibleJButton();
/*     */     }
/* 293 */     return this.accessibleContext;
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
/*     */ 
/*     */   
/*     */   protected class AccessibleJButton
/*     */     extends AbstractButton.AccessibleAbstractButton
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 322 */       return AccessibleRole.PUSH_BUTTON;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */