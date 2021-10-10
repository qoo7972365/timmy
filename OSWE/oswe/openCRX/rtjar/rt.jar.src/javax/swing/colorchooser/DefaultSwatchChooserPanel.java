/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultSwatchChooserPanel
/*     */   extends AbstractColorChooserPanel
/*     */ {
/*     */   SwatchPanel swatchPanel;
/*     */   RecentSwatchPanel recentSwatchPanel;
/*     */   MouseListener mainSwatchListener;
/*     */   MouseListener recentSwatchListener;
/*     */   private KeyListener mainSwatchKeyListener;
/*     */   private KeyListener recentSwatchKeyListener;
/*     */   
/*     */   public DefaultSwatchChooserPanel() {
/*  65 */     setInheritsPopupMenu(true);
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/*  69 */     return UIManager.getString("ColorChooser.swatchesNameText", getLocale());
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
/*     */   public int getMnemonic() {
/*  92 */     return getInt("ColorChooser.swatchesMnemonic", -1);
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
/*     */ 
/*     */   
/*     */   public int getDisplayedMnemonicIndex() {
/* 120 */     return getInt("ColorChooser.swatchesDisplayedMnemonicIndex", -1);
/*     */   }
/*     */   
/*     */   public Icon getSmallDisplayIcon() {
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public Icon getLargeDisplayIcon() {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installChooserPanel(JColorChooser paramJColorChooser) {
/* 136 */     super.installChooserPanel(paramJColorChooser);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildChooser() {
/* 141 */     String str = UIManager.getString("ColorChooser.swatchesRecentText", getLocale());
/*     */     
/* 143 */     GridBagLayout gridBagLayout = new GridBagLayout();
/* 144 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 145 */     JPanel jPanel1 = new JPanel(gridBagLayout);
/*     */     
/* 147 */     this.swatchPanel = new MainSwatchPanel();
/* 148 */     this.swatchPanel.putClientProperty("AccessibleName", 
/* 149 */         getDisplayName());
/* 150 */     this.swatchPanel.setInheritsPopupMenu(true);
/*     */     
/* 152 */     this.recentSwatchPanel = new RecentSwatchPanel();
/* 153 */     this.recentSwatchPanel.putClientProperty("AccessibleName", str);
/*     */ 
/*     */     
/* 156 */     this.mainSwatchKeyListener = new MainSwatchKeyListener();
/* 157 */     this.mainSwatchListener = new MainSwatchListener();
/* 158 */     this.swatchPanel.addMouseListener(this.mainSwatchListener);
/* 159 */     this.swatchPanel.addKeyListener(this.mainSwatchKeyListener);
/* 160 */     this.recentSwatchListener = new RecentSwatchListener();
/* 161 */     this.recentSwatchKeyListener = new RecentSwatchKeyListener();
/* 162 */     this.recentSwatchPanel.addMouseListener(this.recentSwatchListener);
/* 163 */     this.recentSwatchPanel.addKeyListener(this.recentSwatchKeyListener);
/*     */     
/* 165 */     JPanel jPanel2 = new JPanel(new BorderLayout());
/* 166 */     CompoundBorder compoundBorder = new CompoundBorder(new LineBorder(Color.black), new LineBorder(Color.white));
/*     */     
/* 168 */     jPanel2.setBorder(compoundBorder);
/* 169 */     jPanel2.add(this.swatchPanel, "Center");
/*     */     
/* 171 */     gridBagConstraints.anchor = 25;
/* 172 */     gridBagConstraints.gridwidth = 1;
/* 173 */     gridBagConstraints.gridheight = 2;
/* 174 */     Insets insets = gridBagConstraints.insets;
/* 175 */     gridBagConstraints.insets = new Insets(0, 0, 0, 10);
/* 176 */     jPanel1.add(jPanel2, gridBagConstraints);
/* 177 */     gridBagConstraints.insets = insets;
/*     */     
/* 179 */     this.recentSwatchPanel.setInheritsPopupMenu(true);
/* 180 */     JPanel jPanel3 = new JPanel(new BorderLayout());
/* 181 */     jPanel3.setBorder(compoundBorder);
/* 182 */     jPanel3.setInheritsPopupMenu(true);
/* 183 */     jPanel3.add(this.recentSwatchPanel, "Center");
/*     */     
/* 185 */     JLabel jLabel = new JLabel(str);
/* 186 */     jLabel.setLabelFor(this.recentSwatchPanel);
/*     */     
/* 188 */     gridBagConstraints.gridwidth = 0;
/* 189 */     gridBagConstraints.gridheight = 1;
/* 190 */     gridBagConstraints.weighty = 1.0D;
/* 191 */     jPanel1.add(jLabel, gridBagConstraints);
/*     */     
/* 193 */     gridBagConstraints.weighty = 0.0D;
/* 194 */     gridBagConstraints.gridheight = 0;
/* 195 */     gridBagConstraints.insets = new Insets(0, 0, 0, 2);
/* 196 */     jPanel1.add(jPanel3, gridBagConstraints);
/* 197 */     jPanel1.setInheritsPopupMenu(true);
/*     */     
/* 199 */     add(jPanel1);
/*     */   }
/*     */   
/*     */   public void uninstallChooserPanel(JColorChooser paramJColorChooser) {
/* 203 */     super.uninstallChooserPanel(paramJColorChooser);
/* 204 */     this.swatchPanel.removeMouseListener(this.mainSwatchListener);
/* 205 */     this.swatchPanel.removeKeyListener(this.mainSwatchKeyListener);
/* 206 */     this.recentSwatchPanel.removeMouseListener(this.recentSwatchListener);
/* 207 */     this.recentSwatchPanel.removeKeyListener(this.recentSwatchKeyListener);
/*     */     
/* 209 */     this.swatchPanel = null;
/* 210 */     this.recentSwatchPanel = null;
/* 211 */     this.mainSwatchListener = null;
/* 212 */     this.mainSwatchKeyListener = null;
/* 213 */     this.recentSwatchListener = null;
/* 214 */     this.recentSwatchKeyListener = null;
/*     */     
/* 216 */     removeAll();
/*     */   }
/*     */   
/*     */   public void updateChooser() {}
/*     */   
/*     */   private class RecentSwatchKeyListener
/*     */     extends KeyAdapter {
/*     */     private RecentSwatchKeyListener() {}
/*     */     
/*     */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 226 */       if (32 == param1KeyEvent.getKeyCode()) {
/* 227 */         Color color = DefaultSwatchChooserPanel.this.recentSwatchPanel.getSelectedColor();
/* 228 */         DefaultSwatchChooserPanel.this.setSelectedColor(color);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class MainSwatchKeyListener extends KeyAdapter {
/*     */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 235 */       if (32 == param1KeyEvent.getKeyCode()) {
/* 236 */         Color color = DefaultSwatchChooserPanel.this.swatchPanel.getSelectedColor();
/* 237 */         DefaultSwatchChooserPanel.this.setSelectedColor(color);
/* 238 */         DefaultSwatchChooserPanel.this.recentSwatchPanel.setMostRecentColor(color);
/*     */       } 
/*     */     }
/*     */     
/*     */     private MainSwatchKeyListener() {} }
/*     */   
/*     */   class RecentSwatchListener extends MouseAdapter implements Serializable { public void mousePressed(MouseEvent param1MouseEvent) {
/* 245 */       if (DefaultSwatchChooserPanel.this.isEnabled()) {
/* 246 */         Color color = DefaultSwatchChooserPanel.this.recentSwatchPanel.getColorForLocation(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 247 */         DefaultSwatchChooserPanel.this.recentSwatchPanel.setSelectedColorFromLocation(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 248 */         DefaultSwatchChooserPanel.this.setSelectedColor(color);
/* 249 */         DefaultSwatchChooserPanel.this.recentSwatchPanel.requestFocusInWindow();
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   class MainSwatchListener extends MouseAdapter implements Serializable {
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 256 */       if (DefaultSwatchChooserPanel.this.isEnabled()) {
/* 257 */         Color color = DefaultSwatchChooserPanel.this.swatchPanel.getColorForLocation(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 258 */         DefaultSwatchChooserPanel.this.setSelectedColor(color);
/* 259 */         DefaultSwatchChooserPanel.this.swatchPanel.setSelectedColorFromLocation(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 260 */         DefaultSwatchChooserPanel.this.recentSwatchPanel.setMostRecentColor(color);
/* 261 */         DefaultSwatchChooserPanel.this.swatchPanel.requestFocusInWindow();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/DefaultSwatchChooserPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */