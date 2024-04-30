package server;

import common.utility.Outputter;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.FileManager;
import server.utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class App {

    public static final int PORT = 54353;
    public static final int connectionTimeout = 60 * 1000;
    public static final Logger logger = Logger.getLogger(Server.class.getName());


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setEncoding("UTF-8");
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
        String fileName = "C:\\Users\\User\\Documents\\lab6\\server\\src\\main\\java\\server\\data.csv";
        FileManager fileManager = new FileManager(fileName, ";");
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(collectionManager);
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, connectionTimeout, requestHandler);

        server.run();

        collectionManager.saveCollection();



    }
}
