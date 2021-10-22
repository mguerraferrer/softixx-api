package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import softixx.api.util.UPaginate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataLimitBean {
	protected int page = UPaginate.DEFAULT_PAGE;
	protected int offset = UPaginate.DEFAULT_PAGE;
	protected int limit = UPaginate.DEFAULT_PAGE_SIZE;
	protected int totalRows = 0;
	
	public DataLimitBean(int limit) {
		this.limit = limit;
	}
	
	public DataLimitBean(int page, int limit) {
		this.page = page;
		this.offset = page * limit;
		this.limit = limit;
	}
	
	public DataLimitBean(int page, int limit, int totalRows) {
		this.page = page;
		this.offset = page * limit;
		this.limit = limit;
		this.totalRows = totalRows;
	}
	
}