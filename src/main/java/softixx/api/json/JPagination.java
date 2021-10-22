package softixx.api.json;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JPagination<T> {

	private JPage<T> page;
	private JResponse response;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JPage<T> {
		private int size;
		private int totalPages;
		private boolean first;
		private boolean last;
		@Getter(AccessLevel.PUBLIC)
		private boolean hasPrevious;
		@Getter(AccessLevel.PUBLIC)
		private boolean hasNext;
		private int number;
		private int numberOfElements;
		private int totalElements;
		@Getter(AccessLevel.PUBLIC)
		private Boolean hasContent;
		@Singular("content")
		private List<T> content;
		private List<?> customContent;
		private String pageRecords;
		private String pageInfo;
		
		public boolean hasContent() {
			return this.hasContent;
		}
		
		public boolean hasPrevious() {
			return this.hasPrevious;
		}
		
		public boolean hasNext() {
			return this.hasNext;
		}
	}
	
	public JPagination<T> paginationJson(final JPage<T> pageJson, final JResponse response) {
		val paginationJson = new JPagination<T>();
		paginationJson.setPage(pageJson);
		paginationJson.setResponse(response);
		return paginationJson;
	}
	
}