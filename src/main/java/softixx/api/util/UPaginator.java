package softixx.api.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.NoArgsConstructor;
import lombok.val;
import softixx.api.bean.DataLimitBean;
import softixx.api.bean.PageBean;
import softixx.api.json.JPagination.JPage;

@NoArgsConstructor
public class UPaginator<T> {
	private static final Logger log = LoggerFactory.getLogger(UPaginator.class);

	protected Page<T> page;
	protected List<T> content;
	protected JPage<T> pageJson;
	
	public JPage<T> page(final PageBean<T> pageBean) {
		this.page = pageBean.getPage();
		this.content = pageBean.getContent();
		return populatePage();
	}
	
	public JPage<T> customPage(final PageBean<T> pageBean) {
		this.content = pageBean.getContent();
		return populatePage(pageBean.getDataLimitBean());
	}
	
	public JPage<T> populatePage(final Page<T> page) {
		if(page == null) {
			this.page = Page.empty();
		}
		
		this.page = page;
		this.content = page.getContent();
		return populatePage();
	}
	
	@SuppressWarnings("unchecked")
	public JPage<T> populatePage(final List<T> content, final Pageable pageable) {
		this.page = UPage.toPage(content, pageable);
		this.content = page.getContent();
		return populatePage();
	}
	
	public Page<T> getPage() {
		if(this.page == null) {
            return Page.empty();
        }
		return this.page;
	}
	
	public List<T> content() {
		if(this.content == null) {
			this.content = new ArrayList<>();
		}
		return this.content;
	}
	
	public JPage<T> getPageJson() {
		return this.pageJson;
	}
	
	private JPage<T> populatePage() {
		try {
		
			this.pageJson = new JPage<T>();
			this.pageJson.setFirst(this.page.isFirst());
			this.pageJson.setHasContent(this.page.hasContent());
			this.pageJson.setHasNext(this.page.hasNext());
			this.pageJson.setHasPrevious(this.page.hasPrevious());
			this.pageJson.setLast(this.page.isLast());
			this.pageJson.setNumber(this.page.getNumber());
			this.pageJson.setNumberOfElements(this.page.getNumberOfElements());
			this.pageJson.setTotalElements(UInteger.value(ULong.value(this.page.getTotalElements())));
			this.pageJson.setSize(this.page.getSize());
			this.pageJson.setTotalPages(this.page.getTotalPages());
			this.pageJson.setContent(this.content);
			
			//##### Registros
			String pageRecords = pageRecords(this.page);
			this.pageJson.setPageRecords(pageRecords);
			
			//##### Páginas
			val pageInfo = pageInfo(this.page);
			this.pageJson.setPageInfo(pageInfo);
			
			return this.pageJson; 
			
		} catch (Exception e) {
			log.error("UPaginator#populatePage error {}", e.getMessage());
		}		
		return null;
	}
	
	private JPage<T> populatePage(final DataLimitBean bean) {
		try {
			
			val totalRows = bean.getTotalRows();
			val page = bean.getPage();
			val pageSize = bean.getLimit();
			
			var totalPages = 0;
			if(totalRows > 0) {
				if(totalRows <= pageSize) {
					totalPages = 1;
				} else {
					totalPages = totalRows / pageSize;
					if(totalPages > 0 && totalRows % pageSize > 0) {
						totalPages++;
					}
				}
				
				if(totalPages == 0 && totalRows > 0) {
					totalPages++;
				}
			}
			
			val isFirst = (page == 0 || totalPages == 1);
			val isLast = (totalPages == 1 || page + 1 == totalPages); 
			val hasContent = !this.content.isEmpty();
			val hasNext = hasContent && !isLast;
			val hasPrevious = hasContent && !isFirst;
			val pageNumber = page;
			val numberOfElements = this.content.size();
			
			this.pageJson = new JPage<>();
			this.pageJson.setFirst(isFirst);
			this.pageJson.setHasContent(hasContent);
			this.pageJson.setHasNext(hasNext);
			this.pageJson.setHasPrevious(hasPrevious);
			this.pageJson.setLast(isLast);
			this.pageJson.setNumber(pageNumber);
			this.pageJson.setNumberOfElements(numberOfElements);
			this.pageJson.setTotalElements(totalRows);
			this.pageJson.setSize(pageSize);
			this.pageJson.setTotalPages(totalPages);
			this.pageJson.setContent(this.content);
			
			//##### Registros
			String pageRecords = pageRecords(this.pageJson);
			this.pageJson.setPageRecords(pageRecords);
			
			//##### Páginas
			val pageInfo = pageInfo(this.pageJson);
			this.pageJson.setPageInfo(pageInfo);
			
			return this.pageJson; 
			
		} catch (Exception e) {
			log.error("UPaginator#populatePage error {}", e.getMessage());
		}		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String pageRecords(final Page page) {
		String pageRecords = null;
		if(page.getTotalElements() <= page.getSize()) {
			Object[] params = new Integer[] {UInteger.value(ULong.value(page.getTotalElements()))};
			val message = (page.getTotalElements() == 0) ? "paginate.text.result.empty" : "paginate.text.result.info";
			pageRecords = UMessage.getMessage(message, params);
		} else {
			var init = 1;
			var number = page.getNumber();
			var numberOfElements = page.getNumberOfElements();
			var totalElements = UInteger.value(ULong.value(page.getTotalElements()));
			
			if(totalElements > page.getSize()) {
				if(number > 0) {
					init = page.getSize() * number + 1;
					if(number + 1 == page.getTotalPages()) {
						numberOfElements = totalElements;
					} else {
						number ++;
						numberOfElements *= number;
					}
				}
			}
			
			val params = new Object[] {init, numberOfElements, totalElements};
			pageRecords = UMessage.getMessage("paginate.text.result.info.size", params);
		}
		return pageRecords;
	}
	
	@SuppressWarnings("rawtypes")
	public static String pageRecords(final JPage page) {
		String pageRecords = null;
		if(page.getTotalElements() <= page.getSize()) {
			Object[] params = new Integer[] {UInteger.value(ULong.value(page.getTotalElements()))};
			val message = (page.getTotalElements() == 0) ? "paginate.text.result.empty" : "paginate.text.result.info";
			pageRecords = UMessage.getMessage(message, params);
		} else {
			var init = 1;
			var number = page.getNumber();
			var numberOfElements = page.getNumberOfElements();
			var totalElements = UInteger.value(ULong.value(page.getTotalElements()));
			
			if(totalElements > page.getSize()) {
				if(number > 0) {
					init = page.getSize() * number + 1;
					if(number + 1 == page.getTotalPages()) {
						numberOfElements = totalElements;
					} else {
						number ++;
						numberOfElements *= number;
					}
				}
			}
			
			val params = new Object[] {init, numberOfElements, totalElements};
			pageRecords = UMessage.getMessage("paginate.text.result.info.size", params);
		}
		return pageRecords;
	}
	
	@SuppressWarnings("rawtypes")
	public static String pageInfo(final Page page) {
		String pageInfo = "";
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			pageInfo = UMessage.getMessage("paginate.text.info", params);
		}
		return pageInfo;
	}
	
	@SuppressWarnings("rawtypes")
	public static String pageInfo(final JPage page) {
		String pageInfo = "";
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			pageInfo = UMessage.getMessage("paginate.text.info", params);
		}
		return pageInfo;
	}
}