package softixx.api.bean;

import java.util.List;

import lombok.Getter;

@Getter
public class FileDataBean<T> extends FileBean {
	protected Class<T> clazz;
	protected List<T> source;
	
	public void addClazz(final Class<T> clazz) {
		if (clazz == null) {
			throw new Error("The object class is required");
		}
		this.clazz = clazz;
	}
	
	public void addSource(final List<T> source) {
		if (source == null || source.isEmpty()) {
			throw new Error("The source is required");
		}
		this.source = source;
	}
}