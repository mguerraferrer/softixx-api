package softixx.api.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import softixx.api.bean.DataLimitBean;
import softixx.api.bean.PageBean;
import softixx.api.json.JPagination;
import softixx.api.json.JPagination.JPage;
import softixx.api.json.JResponse;

@Slf4j
public class UPaginator {
	private UPaginator() {
		throw new IllegalStateException("Utility class");
	}
	
	public static <T> JPagination<T> paginationJson(final JPage<T> pageJson, final JResponse response) {
		val paginationJson = new JPagination<T>();
		return paginationJson.paginationJson(pageJson, response);
	}
	
	public <T> JPage<T> page(final PageBean<T> pageBean) {
		return populatePage(pageBean.getPage(), pageBean.getContent());
	}
	
	public static <T> JPage<T> customPage(final PageBean<T> pageBean) {
		if (pageBean == null) {
			return JPage.empty();
		}
		return populatePage(pageBean.getDataLimitBean(), pageBean.getContent());
	}
	
	public static <T> JPage<T> populatePage(final Page<T> page) {
		if(page == null) {
			return JPage.empty();
		}
		return populatePage(page, page.getContent());
	}
	
	public static <T> JPage<T> populatePage(final List<T> content, final Pageable pageable) {
		val page = UPage.toPage(content, pageable);
		return populatePage(page, page.getContent());
	}
	
	public static <T> String pageRecords(final Page<T> page) {
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
			
			if(totalElements > page.getSize() && number > 0) {
				init = page.getSize() * number + 1;
				if(number + 1 == page.getTotalPages()) {
					numberOfElements = totalElements;
				} else {
					number ++;
					numberOfElements *= number;
				}
			}
			
			val params = new Object[] {init, numberOfElements, totalElements};
			pageRecords = UMessage.getMessage("paginate.text.result.info.size", params);
		}
		return pageRecords;
	}
	
	public static <T> String pageRecords(final JPage<T> page) {
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
			
			if(totalElements > page.getSize() && number > 0) {
				init = page.getSize() * number + 1;
				if(number + 1 == page.getTotalPages()) {
					numberOfElements = totalElements;
				} else {
					number ++;
					numberOfElements *= number;
				}
			}
			
			val params = new Object[] {init, numberOfElements, totalElements};
			pageRecords = UMessage.getMessage("paginate.text.result.info.size", params);
		}
		return pageRecords;
	}
	
	public static <T> String pageInfo(final Page<T> page) {
		var pageInfo = "";
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			pageInfo = UMessage.getMessage("paginate.text.info", params);
		}
		return pageInfo;
	}
	
	public static <T> String pageInfo(final JPage<T> page) {
		var pageInfo = "";
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			pageInfo = UMessage.getMessage("paginate.text.info", params);
		}
		return pageInfo;
	}
	
	private static <T> JPage<T> populatePage(Page<T> page, List<T> content) {
		try {
		
			val pageJson = new JPage<T>();
			pageJson.setFirst(page.isFirst());
			pageJson.setHasContent(page.hasContent());
			pageJson.setHasNext(page.hasNext());
			pageJson.setHasPrevious(page.hasPrevious());
			pageJson.setLast(page.isLast());
			pageJson.setNumber(page.getNumber());
			pageJson.setNumberOfElements(page.getNumberOfElements());
			pageJson.setTotalElements(UInteger.value(ULong.value(page.getTotalElements())));
			pageJson.setSize(page.getSize());
			pageJson.setTotalPages(page.getTotalPages());
			pageJson.setContent(content);
			
			//##### Registros
			val pageRecords = pageRecords(page);
			pageJson.setPageRecords(pageRecords);
			
			//##### Páginas
			val pageInfo = pageInfo(page);
			pageJson.setPageInfo(pageInfo);
			
			return pageJson; 
			
		} catch (Exception e) {
			log.error("UPaginator#populatePage error - {}", e.getMessage());
		}		
		return null;
	}
	
	private static <T> JPage<T> populatePage(final DataLimitBean bean, List<T> content) {
		try {
			
			val totalRows = bean.getTotalRows();
			val page = bean.getPage();
			val pageSize = bean.getLimit();
			
			val totalPages = determineTotalPages(totalRows, pageSize);
			
			val isFirst = (page == 0 || totalPages == 1);
			val isLast = (totalPages == 1 || page + 1 == totalPages); 
			val hasContent = !content.isEmpty();
			val hasNext = hasContent && !isLast;
			val hasPrevious = hasContent && !isFirst;
			val pageNumber = page;
			val numberOfElements = content.size();
			
			val pageJson = new JPage<T>();
			pageJson.setFirst(isFirst);
			pageJson.setHasContent(hasContent);
			pageJson.setHasNext(hasNext);
			pageJson.setHasPrevious(hasPrevious);
			pageJson.setLast(isLast);
			pageJson.setNumber(pageNumber);
			pageJson.setNumberOfElements(numberOfElements);
			pageJson.setTotalElements(totalRows);
			pageJson.setSize(pageSize);
			pageJson.setTotalPages(totalPages);
			pageJson.setContent(content);
			
			//##### Registros
			val pageRecords = pageRecords(pageJson);
			pageJson.setPageRecords(pageRecords);
			
			//##### Páginas
			val pageInfo = pageInfo(pageJson);
			pageJson.setPageInfo(pageInfo);
			
			return pageJson; 
			
		} catch (Exception e) {
			log.error("UPaginator#populatePage error - {}", e.getMessage());
		}		
		return null;
	}
	
	private static int determineTotalPages(int totalRows, int pageSize) {
		var totalPages = 0;
		try {
			
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
			
		} catch (Exception e) {
			log.error("--- UPaginator#determineTotalPages - error {}", e.getMessage());
		}
		return totalPages;
	}
	
}