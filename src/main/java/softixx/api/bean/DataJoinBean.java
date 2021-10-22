package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DataJoinBean extends DataBean {
	public static final String INNER_JOIN = "INNER JOIN";
	public static final String LEFT_JOIN = "LEFT JOIN";
	public static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
	public static final String RIGHT_JOIN = "RIGHT JOIN";
	public static final String RIGHT_OUTER_JOIN = "RIGHT OUTER JOIN";
	
	protected String typeJoin = INNER_JOIN;
	protected String tableJoin;
	protected String tableJoinOn;
	protected String tableJoinColumn;
	
	public DataJoinBean(String tableJoin, String tableJoinOn, String tableJoinColumn) {
		this.tableJoin = tableJoin;
		this.tableJoinOn = tableJoinOn;
		this.tableJoinColumn = tableJoinColumn;
	}
}