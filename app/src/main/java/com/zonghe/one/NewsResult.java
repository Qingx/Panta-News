package com.zonghe.one;

import java.util.List;

public class NewsResult {
    private int mStat ;
    private List<News> mNews;

    public int getStat() {
        return mStat;
    }

    public void setStat(int stat) {
        mStat = stat;
    }

    public List<News> getNews() {
        return mNews;
    }

    public void setNews(List<News> news) {
        mNews = news;
    }

    @Override
    public String toString() {
        return "NewsResult{" +
                "mStat=" + mStat +
                ", mNews=" + mNews +
                '}';
    }

    public static class News{
        private String muniquekey;
        private String mTitle;
        private String mDate;
        private String mCategory;
        private String mAuthor_name;
        private String mURL;
        private String mThumbnail_pic_s;
        private String mThumbnail_pic_s02;
        private String mThumbnail_pic_s03;
        public String getMuniquekey() {
            return muniquekey;
        }


        public void setMuniquekey(String muniquekey) {
            this.muniquekey = muniquekey;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            mDate = date;
        }

        public String getCategory() {
            return mCategory;
        }

        public void setCategory(String category) {
            mCategory = category;
        }

        public String getAuthor_name() {
            return mAuthor_name;
        }

        public void setAuthor_name(String author_name) {
            mAuthor_name = author_name;
        }

        public String getURL() {
            return mURL;
        }

        public void setURL(String URL) {
            mURL = URL;
        }

        public String getThumbnail_pic_s() {
            return mThumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            mThumbnail_pic_s = thumbnail_pic_s;
        }

        public String getThumbnail_pic_s02() {
            return mThumbnail_pic_s02;
        }

        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            mThumbnail_pic_s02 = thumbnail_pic_s02;
        }

        public String getThumbnail_pic_s03() {
            return mThumbnail_pic_s03;
        }

        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            mThumbnail_pic_s03 = thumbnail_pic_s03;
        }

        @Override
        public String toString() {
            return "News{" +
                    "muniquekey='" + muniquekey + '\'' +
                    ", mTital='" + mTitle + '\'' +
                    ", mDate='" + mDate + '\'' +
                    ", mCategory='" + mCategory + '\'' +
                    ", mAuthor_name='" + mAuthor_name + '\'' +
                    ", mURL='" + mURL + '\'' +
                    ", mThumbnail_pic_s='" + mThumbnail_pic_s + '\'' +
                    ", mThumbnail_pic_s02='" + mThumbnail_pic_s02 + '\'' +
                    ", mThumbnail_pic_s03='" + mThumbnail_pic_s03 + '\'' +
                    '}';
        }
    }

}
