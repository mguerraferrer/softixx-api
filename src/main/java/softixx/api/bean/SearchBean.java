package softixx.api.bean;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBean {
	private Integer page;
	private Integer pageSize;
	private Map<String, String> params;
	private String request;
}
