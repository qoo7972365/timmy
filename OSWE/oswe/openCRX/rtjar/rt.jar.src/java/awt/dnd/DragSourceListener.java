package java.awt.dnd;

import java.util.EventListener;

public interface DragSourceListener extends EventListener {
  void dragEnter(DragSourceDragEvent paramDragSourceDragEvent);
  
  void dragOver(DragSourceDragEvent paramDragSourceDragEvent);
  
  void dropActionChanged(DragSourceDragEvent paramDragSourceDragEvent);
  
  void dragExit(DragSourceEvent paramDragSourceEvent);
  
  void dragDropEnd(DragSourceDropEvent paramDragSourceDropEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DragSourceListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */