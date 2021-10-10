package com.sun.java.swing.plaf.windows;

import javax.swing.JMenuItem;

interface WindowsMenuItemUIAccessor {
  JMenuItem getMenuItem();
  
  TMSchema.State getState(JMenuItem paramJMenuItem);
  
  TMSchema.Part getPart(JMenuItem paramJMenuItem);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsMenuItemUIAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */