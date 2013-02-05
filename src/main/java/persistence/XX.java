//package persistence;
//
//import java.io.Serializable;
//import java.util.Map;
//
//import org.hibernate.EntityMode;
//import org.hibernate.EntityNameResolver;
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.engine.spi.SessionImplementor;
//import org.hibernate.property.Getter;
//import org.hibernate.tuple.entity.EntityTuplizer;
//
//public class XX implements EntityTuplizer {
//
//	public XX() {
//		System.out.println();
//	}
//	
//	@Override
//	public Object[] getPropertyValues(Object entity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setPropertyValues(Object entity, Object[] values) {
//		// TODO Auto-generated method stub
//
//		System.out.println();
//	}
//
//	@Override
//	public Object getPropertyValue(Object entity, int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object instantiate() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isInstance(Object object) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Class getMappedClass() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Getter getGetter(int i) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public EntityMode getEntityMode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object instantiate(Serializable id) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object instantiate(Serializable id, SessionImplementor session) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Serializable getIdentifier(Object entity) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Serializable getIdentifier(Object entity, SessionImplementor session) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setIdentifier(Object entity, Serializable id) throws HibernateException {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion) {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion, SessionImplementor session) {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public Object getVersion(Object entity) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setPropertyValue(Object entity, int i, Object value) throws HibernateException {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public void setPropertyValue(Object entity, String propertyName, Object value) throws HibernateException {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
//			throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getPropertyValue(Object entity, String propertyName) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session) {
//		// TODO Auto-generated method stub
//		System.out.println();
//	}
//
//	@Override
//	public boolean hasProxy() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Object createProxy(Serializable id, SessionImplementor session) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isLifecycleImplementor() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Class getConcreteProxyClass() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean hasUninitializedLazyProperties(Object entity) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isInstrumented() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public EntityNameResolver[] getEntityNameResolvers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Getter getIdentifierGetter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Getter getVersionGetter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
