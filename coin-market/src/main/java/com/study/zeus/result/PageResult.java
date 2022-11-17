package com.study.zeus.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * PageBean
 *
 * @author lzy
 * @create 2018-06-04 17:28
 **/
public class PageResult<T> {

    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页号
     */
    protected Integer pageNo;
    /**
     * 分页大小
     */
    protected Integer pageSize;

    /**
     * 分页数据的起始位置
     */
    private int offset;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式：asc, desc
     */
    private String order;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回分页起始位置
     *
     * @return
     */
    @JSONField(serialize = false)
    @JsonIgnore
    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public static PageResult checkAndInit(Integer pageNo, Integer pageSize) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNo(null == pageNo || pageNo <= 0 ? DEFAULT_PAGE_NO : pageNo);
        pageResult.setPageSize(null == pageSize || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize);
        return pageResult;
    }


    public static PageResult getPageResult(Integer pageNo, Integer pageSize, Integer totalCount) {
        return getPageResult(pageNo, pageSize, totalCount, null, null);
    }

    public static PageResult getPageResult(Integer pageNo, Integer pageSize, Integer totalCount, String orderBy, String order) {
        PageResult pageResult = PageResult.checkAndInit(pageNo, pageSize);
        pageResult.setOrderBy(orderBy);
        pageResult.setOrder(order);
        pageResult.setPageSize(pageResult.getPageSize());
        pageResult.setTotalCount(totalCount == null ? 0 : totalCount);
        if (pageResult.getTotalCount() == 0) {
            pageResult.setTotalPage(0);
        } else {
            int totalPage = pageResult.getTotalCount() / pageResult.getPageSize();
            if (pageResult.getTotalCount() % pageResult.getPageSize() != 0) {
                totalPage += 1;
            }
            pageResult.setTotalPage(totalPage);
            //解决 pageNo 越界
            if (pageResult.getPageNo() > totalPage) {
                pageResult.setPageNo(totalPage);
            }
            int offset = (pageResult.getPageNo() - 1) * pageResult.getPageSize();
            pageResult.setOffset(offset);
        }
        return pageResult;
    }


    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        if (this.totalCount > 0) {
            this.totalPage = this.totalCount / this.pageSize + (this.totalCount % this.pageSize == 0 ? 0 : 1);
        }
        return totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @JSONField(serialize = false)
    @JsonIgnore
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    public String getOrder() {
        return null == order || "".equals(order) ? ORDER_DESC : order;
    }

    public void setOrder(String order) {
        this.order = order;
        if (null != this.order && !"".equals(order)) {
            order = order.toUpperCase();
            if (ORDER_ASC.equals(order) || ORDER_DESC.equals(order)) {
                return;
            }
            this.order = ORDER_ASC;
        }
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
