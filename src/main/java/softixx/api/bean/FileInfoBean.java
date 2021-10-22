package softixx.api.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoBean {
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	
	private String fileName;
	private String message;
	private String errorCode;
	private String status;
	private String url;
	@Default
	private Integer totalLines = 0;
	@Default
	private Integer validLines = 0;
	@Default
	private Integer insertions = 0;
	@Default
	private Integer updates = 0;
	@Default
	private Integer deletions = 0;
	@Default
	private boolean isUpdate = false;

	public void addMessage(final String fileName, final String message, final String status) {
		this.fileName = fileName;
		this.message = message;
		this.status = status;
	}

	public void addError(final String fileName, final String errorCode, final String message, final String status) {
		this.fileName = fileName;
		this.message = message;
		this.status = status;
	}

	public void addStats(final Integer totalLines, final Integer validLines, final Integer insertions,
			final Integer updates, final Integer deletions) {
		this.totalLines = totalLines;
		this.totalLines = validLines;
		this.insertions = insertions;
		this.updates = updates;
		this.deletions = deletions;
	}
}