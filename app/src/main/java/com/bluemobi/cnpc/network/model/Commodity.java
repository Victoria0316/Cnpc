package com.bluemobi.cnpc.network.model;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class Commodity {

        String name;
        String score;
        String count;
        String price;
        String url;
        String id;
        boolean isColl;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getScore() {
                return score;
        }

        public void setScore(String score) {
                this.score = score;
        }

        public String getCount() {
                return count;
        }

        public void setCount(String count) {
                this.count = count;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public boolean isColl() {
                return isColl;
        }

        public void setIsColl(boolean isColl) {
                this.isColl = isColl;
        }
}
