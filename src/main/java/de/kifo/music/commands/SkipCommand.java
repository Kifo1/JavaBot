package de.kifo.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.kifo.Main;
import de.kifo.music.MusicController;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SkipCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if(!message[0].equalsIgnoreCase("!skip")) return;

        GuildVoiceState state;

        if((state = event.getMember().getVoiceState()) != null) {
            VoiceChannel vc = null;
            if(state.getChannel() != null) {
                vc = state.getChannel().asVoiceChannel();
            }

            if(vc != null) {
                MusicController controller = Main.getInstance().playerManager.getController(vc.getGuild().getIdLong());
                AudioPlayer player = controller.getPlayer();

                player.stopTrack();
                event.getChannel().sendMessage("Das Lied wird übersprungen...").queue();
            }
        }
    }
}
