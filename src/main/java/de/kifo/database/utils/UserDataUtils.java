package de.kifo.database.utils;

import de.kifo.Main;
import org.bson.Document;

public class UserDataUtils {

    public static int getMessages(Long guildId, Long userId) {
        return Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first().getInteger("messages");
    }

    public static int getCommandQuantity(Long guildId, Long userId) {
        return Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first().getInteger("commandQuantity");
    }

    public static int getErrorQuantity(Long guildId, Long userId) {
        return Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first().getInteger("errorQuantity");
    }

    public static void addMessages(Long guildId, Long userId, int messageAmount) {
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("messages", (getMessages(guildId, userId) + messageAmount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static void addCommandQuantity(Long guildId, Long userId, int commandAmount) {
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("commandQuantity", ((getCommandQuantity(guildId, userId)) + commandAmount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static void addErrorQuantity(Long guildId, Long userId, int amount) {
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("errorQuantity", ((getErrorQuantity(guildId, userId)) + amount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static boolean fileExist(Long guildId, Long userId) {
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) return true;

        return false;
    }

    private static Document getFilter(Long guildId, Long userId) {
        return new Document("guildId", guildId).append("userId", userId);
    }
}
