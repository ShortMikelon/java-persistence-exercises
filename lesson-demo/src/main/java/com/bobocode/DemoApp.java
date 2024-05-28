package com.bobocode;

import com.bobocode.bibernate.OrmImpl;
import com.bobocode.entity.Quote;

public class DemoApp {
    public static void main(String[] args) {
        var orm = new OrmImpl("jdbc:postgresql://0.tcp.eu.ngrok.io:13243/postgres", "bobouser", "bobopass");

        var quote = orm.findById(Quote.class, 1);
        System.out.println(quote);
    }

}
