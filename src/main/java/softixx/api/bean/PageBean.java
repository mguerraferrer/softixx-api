package softixx.api.bean;

import softixx.api.util.UPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
    private Page<T> page;
    private List<T> content;
    private DataLimitBean dataLimitBean;

    public PageBean() {
    	this.page = Page.empty();
        this.content = new ArrayList<>();
        this.dataLimitBean = new DataLimitBean();
    }
    
    @SuppressWarnings("unchecked")
	public PageBean(final List<T> content, final Pageable pageable) {
        Page<T> page = UPage.toPage(content, pageable);
        if(page == null || page.isEmpty()) {
            this.page = Page.empty();
            this.content = new ArrayList<>();
        } else {
            this.page = page;
            this.content = this.page.getContent();
        }
    }
    
    public PageBean(final List<T> content, final DataLimitBean dataLimitBean) {
    	this.content = content;
    	this.dataLimitBean = dataLimitBean;
    }

    public Page<T> getPage() {
        if(this.page == null) {
            return Page.empty();
        }
        return this.page;
    }

    public List<T> getContent() {
        if(this.content == null) {
            return new ArrayList<>();
        }
        return this.content;
    }

    public DataLimitBean getDataLimitBean() {
        return this.dataLimitBean;
    }
}
