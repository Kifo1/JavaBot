package de.kifo.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloEvent extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(message[0].equalsIgnoreCase("hello")) {

            if(!event.getMember().getUser().isBot()) {

                if(event.getMember().getNickname() != null) {
                    event.getChannel().sendMessage("Hello " + event.getMember().getNickname()).queue();
                } else {
                    event.getChannel().sendMessage("Hello " + event.getMember().getUser().getName()).queue();
                }
            }
        }
    }
}