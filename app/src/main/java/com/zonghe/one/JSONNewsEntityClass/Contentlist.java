/**
  * Copyright 2019 bejson.com 
  */
package com.zonghe.one.JSONNewsEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2019-04-08 14:56:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Contentlist implements Serializable{

    private String pubDate;
    private String channelName;
    private String desc;
    private String channelId;
    private String link;
    private String id;

    @Override
    public String toString() {
        return "Contentlist{" +
                "pubDate='" + pubDate + '\'' +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", channelId='" + channelId + '\'' +
                ", link='" + link + '\'' +
                ", id='" + id + '\'' +
                ", havePic=" + havePic +
                ", title='" + title + '\'' +
                ", imageurls=" + imageurls +
                ", source='" + source + '\'' +
                ", html='" + html + '\'' +
                '}';
    }

    private boolean havePic;
    private String title;
    private List<Imageurls> imageurls;
    private String source;
    private String html;
    public void setPubDate(String pubDate) {
         this.pubDate = pubDate;
     }
     public String getPubDate() {
         return pubDate;
     }

    public void setChannelName(String channelName) {
         this.channelName = channelName;
     }
     public String getChannelName() {
         return channelName;
     }

    public void setDesc(String desc) {
         this.desc = desc;
     }
     public String getDesc() {
         return desc;
     }

    public void setChannelId(String channelId) {
         this.channelId = channelId;
     }
     public String getChannelId() {
         return channelId;
     }

    public void setLink(String link) {
         this.link = link;
     }
     public String getLink() {
         return link;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setHavePic(boolean havePic) {
         this.havePic = havePic;
     }
     public boolean getHavePic() {
         return havePic;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setImageurls(List<Imageurls> imageurls) {
         this.imageurls = imageurls;
     }
     public List<Imageurls> getImageurls() {
         return imageurls;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setHtml(String html) {
         this.html = html;
     }
     public String getHtml() {
         return html;
     }

}