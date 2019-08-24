package com.dlg.community.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PageVO {

    // 总数据条数
    private int totalItem;
    // 每页显示的最大数据条数
    private int itemOfOnePage;
    // 总页数
    private int totalPage;
    // 最大分页按钮数
    private int maxPageButtonCount;

    // 是否显示前一页按钮
    private boolean showPrevious;
    // 是否显示前往第一页按钮
    private boolean showFirst;

    private boolean showNext;
    private boolean showLast;

    int currentPage;

    List<Integer> pageNumberList = new ArrayList<>();
    List<QuestionVO> currentPageItem;


    public void setPage(int currentPage, int itemOfOnePage){

        this.itemOfOnePage = itemOfOnePage;

        totalPage = totalItem % itemOfOnePage == 0 ? totalItem / itemOfOnePage : totalItem / itemOfOnePage + 1;

        int i;
        int j = 0;
        i = currentPage;
        while(i <= totalPage && j <= 2){
            pageNumberList.add(i);
            i++;
            j++;
        }
        i=currentPage - 1;
        j = 0;
        while(i >= 1 && j < 2){
            pageNumberList.add(0, i);
            i--;
            j++;
        }

        if(!pageNumberList.contains(1)){
            showFirst = true;
        }
        if(currentPage != 1){
            showPrevious = true;
        }
        if(!pageNumberList.contains(totalPage)){
            showLast = true;
        }
        if(currentPage != totalPage){
            showNext = true;
        }
    }
}
