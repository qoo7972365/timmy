package javax.print;

public interface CancelablePrintJob extends DocPrintJob {
  void cancel() throws PrintException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/CancelablePrintJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */