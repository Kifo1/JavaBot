package de.kifo.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserInfoCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(!message[0].equalsIgnoreCase("!info")) return;

        if(message.length == 2) {
            String userName = message[1];
            User user;
            String avatar;

            List<Member> list = event.getGuild().getMembersByName(userName, true);
            if(list.isEmpty()) {
                event.getChannel().sendMessage("Der Nutzer konnte nicht gefunden werden.").queue();
                return;
            } else {
                user = list.get(0).getUser();
                avatar = user.getAvatarUrl();
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.MAGENTA);
            builder.setTitle(userName);
            builder.addField("Name", user.getName(), true);
            builder.addField("Online Status: ", event.getGuild().getMembersByName(userName, true).get(0).getOnlineStatus().toString(), true);
            builder.addField("Avatar: ", "The Avatar ist below, " + event.getMember().getAsMention(), true);
            builder.setImage(avatar);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            builder.setFooter(format.format(date));

            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("Verwendung: !info <Name>").queue();
        }
    }

}
