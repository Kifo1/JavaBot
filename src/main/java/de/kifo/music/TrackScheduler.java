package de.kifo.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.kifo.Main;
import de.kifo.music.commands.PlayCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class TrackScheduler extends AudioEventAdapter {

    @Override
    public void onPlayerPause(AudioPlayer player) {

    }

    @Override
    public void onPlayerResume(AudioPlayer player) {

    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        long guildid = Main.getInstance().getPlayerManager().getGuildByPlayerHash(player.hashCode());
        Guild guild = Main.getInstance().getJDA().getGuildById(guildid);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.MAGENTA);
        AudioTrackInfo info = track.getInfo();
        builder.setDescription(guild.getJDA().getEmojiById(599170445287096322L).getAsMention() + " Jetzt läuft: " + info.title);

        long sekunden = info.length/1000;
        long minuten = sekunden/60;
        long stunden = minuten/60;
        minuten %= 60;
        sekunden %= 60;

        String url = info.uri;
        builder.addField(info.author, "[" + info.title +"](" + url + ")", false);
        builder.addField("Länge", info.isStream ? ":red_circle: Stream" : (stunden > 0 ? stunden + "h " : "") + minuten + "min " + sekunden + "s", true);

        //if(url.startsWith("https://www.youtube.com/watch?v=")) {
            //String videoID = url.replace("https://www.youtube.com/watch?v=", "");

            //InputStream file;
            //try{
              //  file = new URL("https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg").openStream();
            //} catch(IOException e) { }
        //}

        TextChannel channel = PlayCommand.map.get(guildid);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    //@Override
    //public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    //}
}
