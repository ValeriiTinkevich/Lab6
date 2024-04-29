package server;

import common.exceptions.ClosingSocketException;
import common.exceptions.ConnectionErrorException;
import common.exceptions.OpeningServerSocketException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseResult;
import common.utility.Outputter;
import server.utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    private final int port;
    private final int soTimeout;
    private ServerSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

    public void run() {
        try {
            openServerSocket();
            boolean processingStatus = true;
            while(processingStatus) {
                try(Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException e) {
                    break;
                } catch (IOException e) {
                    Outputter.printError("An error occurred while trying to terminate the connection with the client!");
                    App.logger.severe("An error occurred while trying to terminate the connection with the client!");
                }

            }

            stop();
        } catch (OpeningServerSocketException e) {
            Outputter.printError("The server cannot be started!");
            App.logger.severe("The server cannot be started!");
        }
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try{
            App.logger.info("Starting the server...");
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            App.logger.info("The server has been successfully started.");
        } catch (IllegalArgumentException exception) {
            Outputter.printError("Port '" + port + "' is beyond the limits of possible values!");
            App.logger.severe("Port '" + port + "' is beyond the limits of possible values!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputter.printError("An error occurred while trying to use the port '" + port + "'!");
            App.logger.severe("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try{
            Outputter.printLn("Listening port '" + port + "'...");
            App.logger.info("Listening port '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputter.printLn("The connection with the client has been successfully established.");
            App.logger.info("The connection with the client has been successfully established.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Outputter.printError("Connection timeout exceeded!");
            App.logger.warning("Connection timeout exceeded!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Outputter.printError("An error occurred while connecting to the client!");
            App.logger.severe("An error occurred while connecting to the client!");
            throw new ConnectionErrorException();
        }
    }

    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                App.logger.info("Request '" + userRequest.getCommandName() + "' has been successfully processed.");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while(responseToUser.getResponseResult() != ResponseResult.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException exception){
            Outputter.printError("An error occurred while reading the received data!");
            App.logger.severe("An error occurred while reading the received data!");

        } catch (IOException exception) {
            if (userRequest == null) {
                Outputter.printError("Unexpected disconnection from the client!");
                App.logger.warning("Unexpected disconnection from the client!");
            } else {
                Outputter.printLn("The client has been successfully disconnected from the server!");
                App.logger.info("The client has been successfully disconnected from the server!");
            }
        }
        return true;
    }

    /**
     * Finishes server operation.
     */
    private void stop() {
        try{
            App.logger.info("Shutting down the server...");
            if(serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            Outputter.printLn("The server operation has been successfully completed.");
        } catch (ClosingSocketException exception) {
            Outputter.printError("It is impossible to shut down a server that has not yet started!");
            App.logger.severe("It is impossible to shut down a server that has not yet started!");
        } catch (IOException exception) {
            Outputter.printError("An error occurred when shutting down the server!");
            App.logger.severe("An error occurred when shutting down the server!");
        }
    }
}
