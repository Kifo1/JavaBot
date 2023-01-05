package de.kifo.database.utils;

import de.kifo.Main;
import org.bson.Document;

public class UserErrorsDataUtils {

    public static int getWrongUsageErrors(Long guildId, Long userId) {
        Document document = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();
        Document object = (Document) document.get("errors");

        return object.getInteger("wrongUsage");
    }

    public static int getNotCommandErrors(Long guildId, Long userId) {
        Document document = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();
        Document object = (Document) document.get("errors");

        return object.getInteger("notCommand");
    }

    public static int getWrongChannelErrors(Long guildId, Long userId) {
        Document document = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();
        Document object = (Document) document.get("errors");

        return object.getInteger("wrongChannel");
    }



    public static void addWrongUsageError(Long guildId, Long userId, int amount) {
        UserDataUtils.addErrorQuantity(guildId, userId, amount);
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("errors.wrongUsage", (getWrongUsageErrors(guildId, userId) + amount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static void addNotCommandError(Long guildId, Long userId, int amount) {
        UserDataUtils.addErrorQuantity(guildId, userId, amount);
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("errors.notCommand", (getNotCommandErrors(guildId, userId) + amount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static void addWrongChannelError(Long guildId, Long userId, int amount) {
        UserDataUtils.addErrorQuantity(guildId, userId, amount);
        Document foundDocument = Main.getInstance().getDataConnection().getCollection().find(getFilter(guildId, userId)).first();

        if(foundDocument != null) {
            Document updateValue = new Document("errors.wrongChannel", (getWrongChannelErrors(guildId, userId) + amount));
            Document updateOperation = new Document("$set", updateValue);

            Main.getInstance().getDataConnection().getCollection().updateOne(foundDocument, updateOperation);
        }
    }

    public static Document getFilter(Long guildId, Long userId) {
        return new Document("guildId", guildId).append("userId", userId);
    }
}
