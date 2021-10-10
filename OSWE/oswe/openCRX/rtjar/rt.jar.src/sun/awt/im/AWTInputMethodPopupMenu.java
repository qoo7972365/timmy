/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.CheckboxMenuItem;
/*     */ import java.awt.Component;
/*     */ import java.awt.Menu;
/*     */ import java.awt.MenuItem;
/*     */ import java.awt.PopupMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AWTInputMethodPopupMenu
/*     */   extends InputMethodPopupMenu
/*     */ {
/* 225 */   static PopupMenu delegate = null;
/*     */   
/*     */   AWTInputMethodPopupMenu(String paramString) {
/* 228 */     synchronized (this) {
/* 229 */       if (delegate == null) {
/* 230 */         delegate = new PopupMenu(paramString);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void show(Component paramComponent, int paramInt1, int paramInt2) {
/* 236 */     delegate.show(paramComponent, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   void removeAll() {
/* 240 */     delegate.removeAll();
/*     */   }
/*     */   
/*     */   void addSeparator() {
/* 244 */     delegate.addSeparator();
/*     */   }
/*     */   
/*     */   void addToComponent(Component paramComponent) {
/* 248 */     paramComponent.add(delegate);
/*     */   }
/*     */   
/*     */   Object createSubmenu(String paramString) {
/* 252 */     return new Menu(paramString);
/*     */   }
/*     */   
/*     */   void add(Object paramObject) {
/* 256 */     delegate.add((MenuItem)paramObject);
/*     */   }
/*     */   
/*     */   void addMenuItem(String paramString1, String paramString2, String paramString3) {
/* 260 */     addMenuItem(delegate, paramString1, paramString2, paramString3);
/*     */   }
/*     */   
/*     */   void addMenuItem(Object paramObject, String paramString1, String paramString2, String paramString3) {
/*     */     MenuItem menuItem;
/* 265 */     if (isSelected(paramString2, paramString3)) {
/* 266 */       menuItem = new CheckboxMenuItem(paramString1, true);
/*     */     } else {
/* 268 */       menuItem = new MenuItem(paramString1);
/*     */     } 
/* 270 */     menuItem.setActionCommand(paramString2);
/* 271 */     menuItem.addActionListener(this);
/* 272 */     menuItem.setEnabled((paramString2 != null));
/* 273 */     ((Menu)paramObject).add(menuItem);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/AWTInputMethodPopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */