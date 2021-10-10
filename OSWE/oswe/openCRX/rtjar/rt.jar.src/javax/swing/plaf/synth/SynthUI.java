package javax.swing.plaf.synth;

import java.awt.Graphics;
import javax.swing.JComponent;

public interface SynthUI extends SynthConstants {
  SynthContext getContext(JComponent paramJComponent);
  
  void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */