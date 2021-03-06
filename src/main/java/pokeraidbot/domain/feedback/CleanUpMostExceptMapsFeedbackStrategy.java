package pokeraidbot.domain.feedback;

import com.jagrosh.jdautilities.commandclient.CommandEvent;
import main.BotServerMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.Validate;
import pokeraidbot.domain.config.LocaleService;
import pokeraidbot.infrastructure.jpa.config.Config;

import java.util.concurrent.TimeUnit;

public class CleanUpMostExceptMapsFeedbackStrategy implements FeedbackStrategy {
    public CleanUpMostExceptMapsFeedbackStrategy() {
    }

    @Override
    public void reply(Config config, CommandEvent commandEvent, String message) {
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(message);
            commandEvent.reactSuccess();
        } else {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor(null, null, null);
            embedBuilder.setTitle(null);
            embedBuilder.setDescription(message);
            final MessageEmbed messageEmbed = embedBuilder.build();
            replyThenDeleteFeedbackAndOriginMessageAfterXTime(commandEvent, messageEmbed,
                    BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public void replyAndKeep(Config config, CommandEvent commandEvent, String message) {
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(message);
            commandEvent.reactSuccess();
        } else {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor(null, null, null);
            embedBuilder.setTitle(null);
            embedBuilder.setDescription(message);
            final MessageEmbed messageEmbed = embedBuilder.build();
            replyThenDeleteFeedbackAndOriginMessageAfterXTime(commandEvent,
                    messageEmbed, BotServerMain.timeToRemoveFeedbackInSeconds * 3, TimeUnit.SECONDS);
        }
    }

    @Override
    public void reply(Config config, CommandEvent commandEvent, MessageEmbed message) {
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(message);
            commandEvent.reactSuccess();
        } else {
            replyThenDeleteFeedbackAndOriginMessageAfterXTime(commandEvent, message,
                    BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public void replyMap(Config config, CommandEvent commandEvent, MessageEmbed message) {
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(message);
            commandEvent.reactSuccess();
        } else {
            commandEvent.getChannel().sendMessage(message).queue(m -> {
                m.delete().queueAfter(BotServerMain.timeToRemoveFeedbackInSeconds * 4, TimeUnit.SECONDS);
            });
            commandEvent.getChannel().deleteMessageById(commandEvent.getMessage().getId()).queueAfter(
                    BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS
            );
        }
    }

    @Override
    public void handleOriginMessage(CommandEvent commandEvent) {
        commandEvent.getMessage().delete()
                .queueAfter(BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void handleOriginMessage(GuildMessageReceivedEvent event) {
        event.getMessage().delete()
                .queueAfter(BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void replyError(Config config, CommandEvent commandEvent, Throwable throwable, LocaleService localeService) {
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(throwable.getMessage());
            commandEvent.reactError();
        } else {
            commandEvent.reactError();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor(null, null, null);
            embedBuilder.setTitle(null);
            embedBuilder.setDescription(throwable.getMessage());
            final String msgRemoveText = localeService.getMessageFor(LocaleService.ERROR_KEEP_CHAT_CLEAN,
                    localeService.getLocaleForUser(commandEvent.getAuthor()),
                    String.valueOf(BotServerMain.timeToRemoveFeedbackInSeconds));
            embedBuilder.setFooter(msgRemoveText, null);
            final MessageEmbed messageEmbed = embedBuilder.build();
            replyThenDeleteFeedbackAndOriginMessageAfterXTime(commandEvent, messageEmbed,
                    BotServerMain.timeToRemoveFeedbackInSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public void reply(Config config, CommandEvent commandEvent, String message, int numberOfSecondsBeforeRemove,
                      LocaleService localeService) {
        Validate.isTrue(numberOfSecondsBeforeRemove > 5);
        if (config != null && config.getReplyInDmWhenPossible()) {
            commandEvent.replyInDM(message);
            commandEvent.reactSuccess();
        } else {
            commandEvent.reactSuccess();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor(null, null, null);
            embedBuilder.setTitle(null);
            embedBuilder.setDescription(message);
            final String msgRemoveText = localeService.getMessageFor(LocaleService.KEEP_CHAT_CLEAN,
                    localeService.getLocaleForUser(commandEvent.getAuthor()), "" +
                            numberOfSecondsBeforeRemove);

            embedBuilder.setFooter(msgRemoveText, null);
            final MessageEmbed messageEmbed = embedBuilder.build();
            replyThenDeleteFeedbackAndOriginMessageAfterXTime(commandEvent, messageEmbed, numberOfSecondsBeforeRemove,
                    TimeUnit.SECONDS);
        }
    }

}
