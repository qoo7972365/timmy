package javax.print.event;

public interface PrintJobListener {
  void printDataTransferCompleted(PrintJobEvent paramPrintJobEvent);
  
  void printJobCompleted(PrintJobEvent paramPrintJobEvent);
  
  void printJobFailed(PrintJobEvent paramPrintJobEvent);
  
  void printJobCanceled(PrintJobEvent paramPrintJobEvent);
  
  void printJobNoMoreEvents(PrintJobEvent paramPrintJobEvent);
  
  void printJobRequiresAttention(PrintJobEvent paramPrintJobEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/event/PrintJobListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */