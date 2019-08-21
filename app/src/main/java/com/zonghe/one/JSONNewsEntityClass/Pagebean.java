/**
  * Copyright 2019 bejson.com 
  */
package com.zonghe.one.JSONNewsEntityClass;
import java.util.List;

/**
 * Auto-generated: 2019-04-08 14:56:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Pagebean {

    private int allPages;

    @Override
    public String toString() {
        return "Pagebean{" +
                "allPages=" + allPages +
                ", contentlist=" + contentlist +
                ", currentPage=" + currentPage +
                ", allNum=" + allNum +
                ", maxResult=" + maxResult +
                '}';
    }

    private List<Contentlist> contentlist;
    private int currentPage;
    private int allNum;
    private int maxResult;
    public void setAllPages(int allPages) {
         this.allPages = allPages;
     }
     public int getAllPages() {
         return allPages;
     }

    public void setContentlist(List<Contentlist> contentlist) {
         this.contentlist = contentlist;
     }
     public List<Contentlist> getContentlist() {
         return contentlist;
     }

    public void setCurrentPage(int currentPage) {
         this.currentPage = currentPage;
     }
     public int getCurrentPage() {
         return currentPage;
     }

    public void setAllNum(int allNum) {
         this.allNum = allNum;
     }
     public int getAllNum() {
         return allNum;
     }

    public void setMaxResult(int maxResult) {
         this.maxResult = maxResult;
     }
     public int getMaxResult() {
         return maxResult;
     }

}