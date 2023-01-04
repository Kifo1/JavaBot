package de.kifo;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import de.kifo.commands.Invite;
import de.kifo.music.PlayerManager;
import de.kifo.music.commands.PlayCommand;
import de.kifo.music.commands.SkipCommand;
import de.kifo.music.commands.StopCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class Main {

    public static AudioPlayerManager audioPlayerManager;
    private static Main instance;
    private static JDA jda;
    public PlayerManager playerManager;


    public static void main(String[] args) {
        audioPlayerManager = new DefaultAudioPlayerManager();

        jda = JDABuilder.createDefault("MTA1OTQzMTY0NDA4MTE3MjQ4MQ.G387y1.UOe97vGVPgjJ4LeuOCRJOEa6U0h77jeN21pBBk")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                .build();
        jda.getPresence().setActivity(Activity.playing("Version: 1.0.0"));

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        //Events
        //jda.addEventListener(new HelloEvent());

        //Commands
        jda.addEventListener(new Invite());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new SkipCommand());

        new Main();
    }

    public Main() {
        instance = this;

        this.playerManager = new PlayerManager();
    }

    public static Main getInstance() {
        return instance;
    }

    public AudioPlayerManager getAudioPlayerManager() {
        return audioPlayerManager;
    }

    public JDA getJDA() {
        return jda;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}