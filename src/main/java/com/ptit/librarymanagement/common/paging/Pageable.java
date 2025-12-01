package com.ptit.librarymanagement.common.paging;



public class Pageable {
    private int totalRecord;
    private int currentPage;
    private int pageSize;
    private int totalPage;



    public Pageable(int totalRecord, int pageSize) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.currentPage = 1;
        this.totalPage = (int) Math.ceil((1.0 * totalRecord / pageSize));
    }


    public Pageable() {
    }

    public void nextPage () {
        if (this.currentPage < this.totalPage)
        this.currentPage++;
    }

    public void prevPage() {
        if (this.currentPage > 1)
        this.currentPage--;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
