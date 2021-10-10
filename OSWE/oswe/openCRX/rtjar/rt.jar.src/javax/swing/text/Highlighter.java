package javax.swing.text;

import java.awt.Graphics;
import java.awt.Shape;

public interface Highlighter {
  void install(JTextComponent paramJTextComponent);
  
  void deinstall(JTextComponent paramJTextComponent);
  
  void paint(Graphics paramGraphics);
  
  Object addHighlight(int paramInt1, int paramInt2, HighlightPainter paramHighlightPainter) throws BadLocationException;
  
  void removeHighlight(Object paramObject);
  
  void removeAllHighlights();
  
  void changeHighlight(Object paramObject, int paramInt1, int paramInt2) throws BadLocationException;
  
  Highlight[] getHighlights();
  
  public static interface Highlight {
    int getStartOffset();
    
    int getEndOffset();
    
    Highlighter.HighlightPainter getPainter();
  }
  
  public static interface HighlightPainter {
    void paint(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent);
  }
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/Highlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */