package ca.ubc.ctlt.ipeerb2;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import static org.mockito.Mockito.*;

public class MockitoSpyFactoryBean implements FactoryBean<Object>, InitializingBean {

	private Object target;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Object getObject() throws Exception {
		if (target == null) {
			return null;
		}

		return spy(target);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<?> getObjectType() {
		return Configuration.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
