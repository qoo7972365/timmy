package javax.print;

import java.io.IOException;

public interface MultiDoc {
  Doc getDoc() throws IOException;
  
  MultiDoc next() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/MultiDoc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */