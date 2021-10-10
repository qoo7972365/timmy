package javax.management.relation;

public interface RelationSupportMBean extends Relation {
  Boolean isInRelationService();
  
  void setRelationServiceManagementFlag(Boolean paramBoolean) throws IllegalArgumentException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RelationSupportMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */