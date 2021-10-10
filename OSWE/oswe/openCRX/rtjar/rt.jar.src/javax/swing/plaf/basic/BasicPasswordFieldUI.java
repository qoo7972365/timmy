/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.PasswordView;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicPasswordFieldUI
/*     */   extends BasicTextFieldUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new BasicPasswordFieldUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  63 */     return "PasswordField";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  72 */     super.installDefaults();
/*  73 */     String str = getPropertyPrefix();
/*  74 */     Character character = (Character)UIManager.getDefaults().get(str + ".echoChar");
/*  75 */     if (character != null) {
/*  76 */       LookAndFeel.installProperty(getComponent(), "echoChar", character);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View create(Element paramElement) {
/*  87 */     return new PasswordView(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ActionMap createActionMap() {
/*  97 */     ActionMap actionMap = super.createActionMap();
/*  98 */     if (actionMap.get("select-word") != null) {
/*  99 */       Action action = actionMap.get("select-line");
/* 100 */       if (action != null) {
/* 101 */         actionMap.remove("select-word");
/* 102 */         actionMap.put("select-word", action);
/*     */       } 
/*     */     } 
/* 105 */     return actionMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicPasswordFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */