/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JInputMethodPopupMenu
/*     */   extends InputMethodPopupMenu
/*     */ {
/* 168 */   static JPopupMenu delegate = null;
/*     */   
/*     */   JInputMethodPopupMenu(String paramString) {
/* 171 */     synchronized (this) {
/* 172 */       if (delegate == null) {
/* 173 */         delegate = new JPopupMenu(paramString);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void show(Component paramComponent, int paramInt1, int paramInt2) {
/* 179 */     delegate.show(paramComponent, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void removeAll() {
/* 183 */     delegate.removeAll();
/*     */   }
/*     */   
/*     */   void addSeparator() {
/* 187 */     delegate.addSeparator();
/*     */   }
/*     */ 
/*     */   
/*     */   void addToComponent(Component paramComponent) {}
/*     */   
/*     */   Object createSubmenu(String paramString) {
/* 194 */     return new JMenu(paramString);
/*     */   }
/*     */   
/*     */   void add(Object paramObject) {
/* 198 */     delegate.add((JMenuItem)paramObject);
/*     */   }
/*     */   
/*     */   void addMenuItem(String paramString1, String paramString2, String paramString3) {
/* 202 */     addMenuItem(delegate, paramString1, paramString2, paramString3);
/*     */   }
/*     */   
/*     */   void addMenuItem(Object paramObject, String paramString1, String paramString2, String paramString3) {
/*     */     JMenuItem jMenuItem;
/* 207 */     if (isSelected(paramString2, paramString3)) {
/* 208 */       jMenuItem = new JCheckBoxMenuItem(paramString1, true);
/*     */     } else {
/* 210 */       jMenuItem = new JMenuItem(paramString1);
/*     */     } 
/* 212 */     jMenuItem.setActionCommand(paramString2);
/* 213 */     jMenuItem.addActionListener(this);
/* 214 */     jMenuItem.setEnabled((paramString2 != null));
/* 215 */     if (paramObject instanceof JMenu) {
/* 216 */       ((JMenu)paramObject).add(jMenuItem);
/*     */     } else {
/* 218 */       ((JPopupMenu)paramObject).add(jMenuItem);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/JInputMethodPopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */