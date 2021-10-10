package javax.swing.plaf;

import javax.swing.JOptionPane;

public abstract class OptionPaneUI extends ComponentUI {
  public abstract void selectInitialValue(JOptionPane paramJOptionPane);
  
  public abstract boolean containsCustomComponents(JOptionPane paramJOptionPane);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/OptionPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */