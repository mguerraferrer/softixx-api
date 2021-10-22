package softixx.api.bean;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Extension of {@link QueryableBean} to provide additional heritable properties
 * to those provided by {@link QueryableBean}
 * 
 * @see QueryableBean
 * @author Maikel Guerra Ferrer
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class CommonBean<T, ID> extends QueryableBean implements Serializable {
	private static final long serialVersionUID = 3954072779966306037L;
	
	protected Class<T> clazz;
	protected Class<ID> idClazz;
	
	protected void addClazz(final Class<T> claz) {
		clazz = claz;
	}
	
	protected void addIdClazz(final Class<ID> idClaz) {
		idClazz = idClaz;
	}

}