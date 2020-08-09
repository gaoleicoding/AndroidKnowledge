package com.example.knowledge.recyclerview.layoutmanager.flow;

import java.util.List;

/**
 * Created by xiangcheng on 17/9/26.
 */

public class Product {
    public List<Classify> classify;

    public static  class Classify {
        public String title;
        List<Des> des;

        public Classify(String title, List<Des> des) {
            this.title = title;
            this.des = des;
        }

        public static class Des {
            boolean isSelect;
            String des;

            public Des(String des) {
                this.des = des;
            }
        }

    }
}
