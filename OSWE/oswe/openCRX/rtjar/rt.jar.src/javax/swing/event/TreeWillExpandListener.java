package javax.swing.event;

import java.util.EventListener;
import javax.swing.tree.ExpandVetoException;

public interface TreeWillExpandListener extends EventListener {
  void treeWillExpand(TreeExpansionEvent paramTreeExpansionEvent) throws ExpandVetoException;
  
  void treeWillCollapse(TreeExpansionEvent paramTreeExpansionEvent) throws ExpandVetoException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/TreeWillExpandListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */