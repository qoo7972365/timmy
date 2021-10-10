/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Objects;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.plaf.ToolTipUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JToolTip
/*     */   extends JComponent
/*     */   implements Accessible
/*     */ {
/*     */   private static final String uiClassID = "ToolTipUI";
/*     */   String tipText;
/*     */   JComponent component;
/*     */   
/*     */   public JToolTip() {
/*  83 */     setOpaque(true);
/*  84 */     updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolTipUI getUI() {
/*  93 */     return (ToolTipUI)this.ui;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 102 */     setUI(UIManager.getUI(this));
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
/* 114 */     return "ToolTipUI";
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
/*     */   public void setTipText(String paramString) {
/* 129 */     String str = this.tipText;
/* 130 */     this.tipText = paramString;
/* 131 */     firePropertyChange("tiptext", str, paramString);
/*     */     
/* 133 */     if (!Objects.equals(str, paramString)) {
/* 134 */       revalidate();
/* 135 */       repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTipText() {
/* 146 */     return this.tipText;
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
/*     */   public void setComponent(JComponent paramJComponent) {
/* 163 */     JComponent jComponent = this.component;
/*     */     
/* 165 */     this.component = paramJComponent;
/* 166 */     firePropertyChange("component", jComponent, paramJComponent);
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
/*     */   public JComponent getComponent() {
/* 178 */     return this.component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean alwaysOnTop() {
/* 187 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 197 */     paramObjectOutputStream.defaultWriteObject();
/* 198 */     if (getUIClassID().equals("ToolTipUI")) {
/* 199 */       byte b = JComponent.getWriteObjCounter(this);
/* 200 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 201 */       if (b == 0 && this.ui != null) {
/* 202 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 219 */     String str = (this.tipText != null) ? this.tipText : "";
/*     */ 
/*     */     
/* 222 */     return super.paramString() + ",tipText=" + str;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 241 */     if (this.accessibleContext == null) {
/* 242 */       this.accessibleContext = new AccessibleJToolTip();
/*     */     }
/* 244 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJToolTip
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public String getAccessibleDescription() {
/* 270 */       String str = this.accessibleDescription;
/*     */ 
/*     */       
/* 273 */       if (str == null) {
/* 274 */         str = (String)JToolTip.this.getClientProperty("AccessibleDescription");
/*     */       }
/* 276 */       if (str == null) {
/* 277 */         str = JToolTip.this.getTipText();
/*     */       }
/* 279 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 289 */       return AccessibleRole.TOOL_TIP;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JToolTip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */