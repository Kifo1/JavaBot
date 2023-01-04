package de.kifo.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(message[0].equalsIgnoreCase("!invite")) {
            event.getChannel().sendMessage("Verschicke diesen Link, um jemanden zum Server einzuladen: ").queue();

            if(event.getChannelType().equals(ChannelType.TEXT)) {
                event.getChannel().sendMessage(event.getChannel().asTextChannel().createInvite().setMaxAge(3600).complete().getUrl()).queue();
            } else if(event.getChannelType().equals(ChannelType.VOICE)) {
                event.getChannel().sendMessage(event.getChannel().asVoiceChannel().createInvite().setMaxAge(3600).complete().getUrl()).queue();
            }

            event.getChannel().sendMessage("Du kannst den Link für eine Stunde nutzen.").queue();
        }
    }
}