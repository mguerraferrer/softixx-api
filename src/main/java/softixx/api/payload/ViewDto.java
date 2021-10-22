package softixx.api.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewDto {
	private String nav;
	private String title;
	private String titleHide;
	private String parent;
	private String parentPath;
	private String sibling;
	private String siblingPath;
}