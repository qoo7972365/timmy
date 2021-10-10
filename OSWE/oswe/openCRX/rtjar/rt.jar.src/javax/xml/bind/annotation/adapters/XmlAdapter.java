package javax.xml.bind.annotation.adapters;

public abstract class XmlAdapter<ValueType, BoundType> {
  public abstract BoundType unmarshal(ValueType paramValueType) throws Exception;
  
  public abstract ValueType marshal(BoundType paramBoundType) throws Exception;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/adapters/XmlAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */