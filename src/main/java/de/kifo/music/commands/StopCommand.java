package de.kifo.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.kifo.Main;
import de.kifo.music.AudioLoadResult;
import de.kifo.music.MusicController;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase("!stop")) return;

        GuildVoiceState state;

        if ((state = event.getMember().getVoiceState()) != null) {
            VoiceChannel vc = null;
            if (state.getChannel() != null) {
                vc = state.getChannel().asVoiceChannel();
            }

            if (vc != null) {
                MusicController controller = Main.getInstance().playerManager.getController(vc.getGuild().getIdLong());
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioPlayer player = controller.getPlayer();

                AudioLoadResult.map.get(event.getGuild()).clear();
                player.stopTrack();
                manager.closeAudioConnection();
                event.getMessage().addReaction(Emoji.fromFormatted("U+1F44C")).queue();
            }
        }
    }
}