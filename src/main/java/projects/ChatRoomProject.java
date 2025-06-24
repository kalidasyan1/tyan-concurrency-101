package projects;

import java.util.concurrent.*;
import java.util.*;

/**
 * A skeleton for a real-time chatroom using synchronized data structures.
 * 	1.	Use ConcurrentHashMap to manage chat rooms.
 * 	2.	Use CopyOnWriteArrayList for participants.
 */
public class ChatRoomProject {
  private final Map<String, CopyOnWriteArrayList<String>> rooms = new ConcurrentHashMap<>();

  public void joinRoom(String room, String user) {
    rooms.computeIfAbsent(room, k -> new CopyOnWriteArrayList<>()).add(user);
    System.out.println(user + " joined " + room);
  }

  public void sendMessage(String room, String user, String message) {
    if (rooms.containsKey(room)) {
      for (String participant : rooms.get(room)) {
        if(!user.equals(participant) || rooms.get(room).size() == 1) {
          System.out.println("[" + room + "] " + user + ": " + message + " (to " + participant + ")");
        }
      }
    }
  }

  public static void main(String[] args) {
    ChatRoomProject chat = new ChatRoomProject();
    chat.joinRoom("General", "Alice");
    chat.joinRoom("General", "Bob");
    chat.joinRoom("General", "Thomas");
    chat.joinRoom("Sub-room-1", "Allen");

    Runnable alice = () -> chat.sendMessage("General", "Alice", "Hi, I'm Alice!");
    Runnable bob = () -> chat.sendMessage("General", "Bob", "Hello Alice! Welcome to the group.");
    Runnable allen = () -> chat.sendMessage("Sub-room-1", "Allen", "Hello, is anyone in the room?");

    new Thread(alice).start();
    new Thread(bob).start();
    new Thread(allen).start();
  }
}
