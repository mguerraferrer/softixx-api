package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import softixx.api.enums.EOrderBy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataOrderByBean extends DataBean {
	protected EOrderBy direction;

	public DataOrderByBean(final String columnName, final EOrderBy direction) {
		this.columnName = columnName;
		this.direction = direction;
	}
}
