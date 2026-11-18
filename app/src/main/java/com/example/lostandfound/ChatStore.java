package com.example.lostandfound;

import java.util.ArrayList;
import java.util.List;

/**
 * The placeholder chats, kept in memory while the app is open. It is the stand-in for the
 * database (Firebase will replace it later): the chat list, the new-message screen and the
 * conversation screens all read and write here, so a message you send shows up everywhere.
 * Everything is forgotten when the app is closed.
 */
final class ChatStore {

    /** One message in a conversation. mine = you sent it (right side), otherwise it is theirs (left side). */
    static final class Message {
        final boolean mine;
        final String text;

        Message(boolean mine, String text) {
            this.mine = mine;
            this.text = text;
        }
    }

    /** A conversation with one other person. The display name comes first, then the username. */
    static final class Chat {
        final String displayName;
        final String username;
        final List<Message> messages = new ArrayList<>();
        /** True after you block this person: you cannot send them messages until you unblock. */
        boolean blocked = false;

        Chat(String displayName, String username) {
            this.displayName = displayName;
            this.username = username;
        }

        /** The latest message is the one shown in the chat list. */
        String preview() {
            if (blocked) {
                return "You blocked this user";
            }
            if (messages.isEmpty()) {
                return "";
            }
            Message latest = messages.get(messages.size() - 1);
            String who = latest.mine ? "You" : displayName.split(" ")[0];
            return who + ": " + latest.text;
        }
    }

    static final String MY_USERNAME = "User12345";

    // Placeholder directory of people: username, then display name. A real app would look
    // these up in the database. Anyone not listed here shows their username as their name.
    private static final String[][] PEOPLE = {
            {"User67890", "Maria Santos"}, {"User54321", "Carlo Reyes"},
            {"User38576", "Anna Cruz"}, {"User42198", "Miguel Tan"},
            {"User67", "Liam Garcia"}, {"User12", "Sofia Reyes"}, {"User88", "Noah Lim"},
            {"User45", "Isabel Cruz"}, {"User203", "Ethan Tan"}, {"User31", "Mia Santos"},
            {"User76", "Lucas Dela Cruz"}, {"User90", "Chloe Ramos"}, {"User18", "Jacob Aquino"},
            {"User55", "Ella Mendoza"}, {"User402", "Gabriel Torres"}, {"User27", "Zoe Villanueva"},
            {"User64", "Daniel Flores"}, {"User09", "Hannah Bautista"}, {"User83", "Ryan Castillo"},
    };

    /** The display name that goes with a username (or the username itself if we do not know them). */
    static String displayNameOf(String username) {
        String wanted = clean(username);
        for (String[] person : PEOPLE) {
            if (clean(person[0]).equals(wanted)) {
                return person[1];
            }
        }
        return username.trim().startsWith("@") ? username.trim().substring(1) : username.trim();
    }

    private static final List<Chat> CHATS = new ArrayList<>();

    static {
        Chat maria = new Chat("Maria Santos", "User67890");
        maria.messages.add(new Message(false, "Hey i found the item you reported."));
        maria.messages.add(new Message(false, "Is it a black leather wallet?"));
        maria.messages.add(new Message(false, "It was on a canteen table."));
        maria.messages.add(new Message(false, "Where can we meet?"));
        maria.messages.add(new Message(true, "Yes, that is mine!"));
        maria.messages.add(new Message(true, "Thank you so much."));
        maria.messages.add(new Message(true, "Can we meet at the guard house?"));
        maria.messages.add(new Message(true, "I am free after class."));

        Chat carlo = new Chat("Carlo Reyes", "User54321");
        carlo.messages.add(new Message(false, "Where can we meet so I can return it?"));
        carlo.messages.add(new Message(true, "How about the guard house after 4?"));
        carlo.messages.add(new Message(false, "Okay, see you there."));

        Chat anna = new Chat("Anna Cruz", "User38576");
        anna.messages.add(new Message(false, "Hi! Did you lose a blue umbrella?"));
        anna.messages.add(new Message(true, "Yes! Where did you find it?"));
        anna.messages.add(new Message(false, "Near the library entrance."));
        anna.messages.add(new Message(true, "Thank you, I will come and get it."));

        Chat miguel = new Chat("Miguel Tan", "User42198");
        miguel.messages.add(new Message(true, "Hello, is the calculator still with you?"));
        miguel.messages.add(new Message(false, "Yes, I have it in the guidance office."));

        CHATS.add(maria);
        CHATS.add(carlo);
        CHATS.add(anna);
        CHATS.add(miguel);
    }

    private ChatStore() {
    }

    /** All chats, the one with the newest message first. */
    static List<Chat> all() {
        return CHATS;
    }

    /** The chat with this username, or null. Capital letters and a leading @ do not matter. */
    static Chat find(String username) {
        String wanted = clean(username);
        for (Chat chat : CHATS) {
            if (clean(chat.username).equals(wanted)) {
                return chat;
            }
        }
        return null;
    }

    /**
     * The chat with this username. A new user has no chat yet, so one is made; we only know the
     * username of a new user, so it is also shown as the display name until they reply.
     */
    static Chat findOrCreate(String username) {
        Chat existing = find(username);
        if (existing != null) {
            return existing;
        }
        String name = username.trim();
        if (name.startsWith("@")) {
            name = name.substring(1);
        }
        Chat created = new Chat(displayNameOf(name), name);
        CHATS.add(0, created);
        return created;
    }

    /** Add a message from you and move that chat to the top of the list. */
    static void send(Chat chat, String text) {
        chat.messages.add(new Message(true, text));
        CHATS.remove(chat);
        CHATS.add(0, chat);
    }

    private static String clean(String username) {
        String name = username.trim().toLowerCase();
        return name.startsWith("@") ? name.substring(1) : name;
    }
}
