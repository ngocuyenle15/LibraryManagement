package com.ptit.librarymanagement.common.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable {
    private int totalRecord;
    private int currenPage;
    private int pageSize;
    private int totalPage;



    public Pageable(int totalRecord, int pageSize) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.currenPage = 1;
        this.totalPage = (int) Math.ceil((1.0 * totalRecord / pageSize));
    }


    public Pageable() {
    }

    public void nextPage () {
        if (this.currenPage < this.totalPage)
        this.currenPage++;
    }

    public void prevPage() {
        if (this.currenPage > 1)
        this.currenPage--;
    }


}
