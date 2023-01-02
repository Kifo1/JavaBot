package de.kifo;

import de.kifo.events.HelloEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static void main(String[] args) {

        JDA jda = JDABuilder.createDefault("MTA1OTQzMTY0NDA4MTE3MjQ4MQ.G387y1.UOe97vGVPgjJ4LeuOCRJOEa6U0h77jeN21pBBk").build();

        jda.addEventListener(new HelloEvent());
    }
}