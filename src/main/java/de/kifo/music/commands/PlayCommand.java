package de.kifo.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import de.kifo.Main;
import de.kifo.music.AudioLoadResult;
import de.kifo.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.util.HashMap;

public class PlayCommand extends ListenerAdapter {

    public static HashMap<Long, TextChannel> map = new HashMap<>();

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(!message[0].equalsIgnoreCase("!play")) return;


        if(message.length > 1) {
            GuildVoiceState state;

            if((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc = null;
                if(state.getChannel() != null) {
                    vc = state.getChannel().asVoiceChannel();
                }

                if(vc != null) {
                    MusicController controller = Main.getInstance().playerManager.getController(vc.getGuild().getIdLong());
                    AudioPlayer player = controller.getPlayer();
                    AudioPlayerManager apm = Main.getInstance().getAudioPlayerManager();
                    AudioManager manager = vc.getGuild().getAudioManager();
                    manager.openAudioConnection(vc);


                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i < message.length; i++) {
                        stringBuilder.append(message[i] + " ");
                    }

                    String url = stringBuilder.toString().trim();
                    if(!url.startsWith("http")) {
                        url = "ytsearch:" + url + " audio";
                        event.getChannel().sendMessage("Suche nach deinen Begriffen...").queue();
                    }

                    apm.loadItem(url, new AudioLoadResult(controller, url, controller.getGuild()));

                    map.put(vc.getGuild().getIdLong(), event.getChannel().asTextChannel());
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Du musst in einem Sprachkanal sein.");
                    builder.setColor(Color.MAGENTA);
                    event.getChannel().sendMessageEmbeds(builder.build()).queue();
                }

            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Du musst in einem Sprachkanal sein.");
                builder.setColor(Color.MAGENTA);
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
            }

        } else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Bitte benutze !play <Url/Schlagwörter>");
            builder.setColor(Color.MAGENTA);
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }
}
