package com.zq.android.data.element;

/**
 * @des:
 * @author: zq
 * @email: qizhou1994@126.com
 * @date: 2017-05-19 17:16
 */
public class News {
    private String id;

    private String title;

    private String source;

    private String firstImg;

    private String mark;

    private String url;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setSource(String source){
        this.source = source;
    }
    public String getSource(){
        return this.source;
    }
    public void setFirstImg(String firstImg){
        this.firstImg = firstImg;
    }
    public String getFirstImg(){
        return this.firstImg;
    }
    public void setMark(String mark){
        this.mark = mark;
    }
    public String getMark(){
        return this.mark;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", mark='" + mark + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
