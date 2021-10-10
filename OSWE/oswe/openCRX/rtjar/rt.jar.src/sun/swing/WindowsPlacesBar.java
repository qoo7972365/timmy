/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ import sun.awt.OSInfo;
/*     */ import sun.awt.shell.ShellFolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsPlacesBar
/*     */   extends JToolBar
/*     */   implements ActionListener, PropertyChangeListener
/*     */ {
/*     */   JFileChooser fc;
/*     */   JToggleButton[] buttons;
/*     */   ButtonGroup buttonGroup;
/*     */   File[] files;
/*     */   final Dimension buttonSize;
/*     */   
/*     */   public WindowsPlacesBar(JFileChooser paramJFileChooser, boolean paramBoolean) {
/*  59 */     super(1);
/*  60 */     this.fc = paramJFileChooser;
/*  61 */     setFloatable(false);
/*  62 */     putClientProperty("JToolBar.isRollover", Boolean.TRUE);
/*     */ 
/*     */     
/*  65 */     boolean bool = (OSInfo.getOSType() == OSInfo.OSType.WINDOWS && OSInfo.getWindowsVersion().compareTo(OSInfo.WINDOWS_XP) >= 0) ? true : false;
/*     */     
/*  67 */     if (paramBoolean) {
/*  68 */       this.buttonSize = new Dimension(83, 69);
/*  69 */       putClientProperty("XPStyle.subAppName", "placesbar");
/*  70 */       setBorder(new EmptyBorder(1, 1, 1, 1));
/*     */     } else {
/*     */       
/*  73 */       this.buttonSize = new Dimension(83, bool ? 65 : 54);
/*  74 */       setBorder(new BevelBorder(1, 
/*  75 */             UIManager.getColor("ToolBar.highlight"), 
/*  76 */             UIManager.getColor("ToolBar.background"), 
/*  77 */             UIManager.getColor("ToolBar.darkShadow"), 
/*  78 */             UIManager.getColor("ToolBar.shadow")));
/*     */     } 
/*  80 */     Color color = new Color(UIManager.getColor("ToolBar.shadow").getRGB());
/*  81 */     setBackground(color);
/*  82 */     FileSystemView fileSystemView = paramJFileChooser.getFileSystemView();
/*     */     
/*  84 */     this.files = (File[])ShellFolder.get("fileChooserShortcutPanelFolders");
/*     */     
/*  86 */     this.buttons = new JToggleButton[this.files.length];
/*  87 */     this.buttonGroup = new ButtonGroup();
/*  88 */     for (byte b = 0; b < this.files.length; b++) {
/*  89 */       Icon icon; if (fileSystemView.isFileSystemRoot(this.files[b]))
/*     */       {
/*  91 */         this.files[b] = fileSystemView.createFileObject(this.files[b].getAbsolutePath());
/*     */       }
/*     */       
/*  94 */       String str = fileSystemView.getSystemDisplayName(this.files[b]);
/*  95 */       int i = str.lastIndexOf(File.separatorChar);
/*  96 */       if (i >= 0 && i < str.length() - 1) {
/*  97 */         str = str.substring(i + 1);
/*     */       }
/*     */       
/* 100 */       if (this.files[b] instanceof ShellFolder) {
/*     */         
/* 102 */         ShellFolder shellFolder = (ShellFolder)this.files[b];
/* 103 */         Image image = shellFolder.getIcon(true);
/*     */         
/* 105 */         if (image == null)
/*     */         {
/* 107 */           image = (Image)ShellFolder.get("shell32LargeIcon 1");
/*     */         }
/*     */         
/* 110 */         icon = (image == null) ? null : new ImageIcon(image, shellFolder.getFolderType());
/*     */       } else {
/* 112 */         icon = fileSystemView.getSystemIcon(this.files[b]);
/*     */       } 
/* 114 */       this.buttons[b] = new JToggleButton(str, icon);
/* 115 */       if (paramBoolean) {
/* 116 */         this.buttons[b].putClientProperty("XPStyle.subAppName", "placesbar");
/*     */       } else {
/* 118 */         Color color1 = new Color(UIManager.getColor("List.selectionForeground").getRGB());
/* 119 */         this.buttons[b].setContentAreaFilled(false);
/* 120 */         this.buttons[b].setForeground(color1);
/*     */       } 
/* 122 */       this.buttons[b].setMargin(new Insets(3, 2, 1, 2));
/* 123 */       this.buttons[b].setFocusPainted(false);
/* 124 */       this.buttons[b].setIconTextGap(0);
/* 125 */       this.buttons[b].setHorizontalTextPosition(0);
/* 126 */       this.buttons[b].setVerticalTextPosition(3);
/* 127 */       this.buttons[b].setAlignmentX(0.5F);
/* 128 */       this.buttons[b].setPreferredSize(this.buttonSize);
/* 129 */       this.buttons[b].setMaximumSize(this.buttonSize);
/* 130 */       this.buttons[b].addActionListener(this);
/* 131 */       add(this.buttons[b]);
/* 132 */       if (b < this.files.length - 1 && paramBoolean) {
/* 133 */         add(Box.createRigidArea(new Dimension(1, 1)));
/*     */       }
/* 135 */       this.buttonGroup.add(this.buttons[b]);
/*     */     } 
/* 137 */     doDirectoryChanged(paramJFileChooser.getCurrentDirectory());
/*     */   }
/*     */   
/*     */   protected void doDirectoryChanged(File paramFile) {
/* 141 */     for (byte b = 0; b < this.buttons.length; b++) {
/* 142 */       JToggleButton jToggleButton = this.buttons[b];
/* 143 */       if (this.files[b].equals(paramFile)) {
/* 144 */         jToggleButton.setSelected(true); break;
/*     */       } 
/* 146 */       if (jToggleButton.isSelected()) {
/*     */ 
/*     */         
/* 149 */         this.buttonGroup.remove(jToggleButton);
/* 150 */         jToggleButton.setSelected(false);
/* 151 */         this.buttonGroup.add(jToggleButton);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 157 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 158 */     if (str == "directoryChanged") {
/* 159 */       doDirectoryChanged(this.fc.getCurrentDirectory());
/*     */     }
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 164 */     JToggleButton jToggleButton = (JToggleButton)paramActionEvent.getSource();
/* 165 */     for (byte b = 0; b < this.buttons.length; b++) {
/* 166 */       if (jToggleButton == this.buttons[b]) {
/* 167 */         this.fc.setCurrentDirectory(this.files[b]);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 174 */     Dimension dimension1 = getMinimumSize();
/* 175 */     Dimension dimension2 = super.getPreferredSize();
/* 176 */     int i = dimension1.height;
/* 177 */     if (this.buttons != null && this.buttons.length > 0 && this.buttons.length < 5) {
/* 178 */       JToggleButton jToggleButton = this.buttons[0];
/* 179 */       if (jToggleButton != null) {
/* 180 */         int j = 5 * ((jToggleButton.getPreferredSize()).height + 1);
/* 181 */         if (j > i) {
/* 182 */           i = j;
/*     */         }
/*     */       } 
/*     */     } 
/* 186 */     if (i > dimension2.height) {
/* 187 */       dimension2 = new Dimension(dimension2.width, i);
/*     */     }
/* 189 */     return dimension2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/WindowsPlacesBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */