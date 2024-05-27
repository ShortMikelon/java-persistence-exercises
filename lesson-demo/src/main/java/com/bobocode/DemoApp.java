package com.bobocode;

import com.bobocode.bibernate.OrmImpl;
import com.bobocode.entity.Participant;

public class DemoApp {
    public static void main(String[] args) {
        var orm = new OrmImpl("jdbc:postgresql://0.tcp.eu.ngrok.io:11026/postgres", "bobouser", "bobopass");

        var participant = orm.findById(Participant.class, 23);
        System.out.println(participant);
    }

}
