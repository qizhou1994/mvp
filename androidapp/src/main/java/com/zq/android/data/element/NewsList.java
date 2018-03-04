package com.zq.android.data.element;

import java.util.List;

/**
 * @des: 微信精选
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:22
 */
public class NewsList {
    private List<News> list ;

    private int totalPage;

    private int ps;

    private int pno;

    public void setList(List<News> list){
        this.list = list;
    }
    public List<News> getList(){
        return this.list;
    }
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
    public int getTotalPage(){
        return this.totalPage;
    }
    public void setPs(int ps){
        this.ps = ps;
    }
    public int getPs(){
        return this.ps;
    }
    public void setPno(int pno){
        this.pno = pno;
    }
    public int getPno(){
        return this.pno;
    }

}
