package ru.otus;

import ru.otus.web.ServerManager;

/**
 * Created by Artem Gabbasov on 22.07.2017.
 * <p>
 */
@SuppressWarnings("WeakerAccess")
public class Main {
    public static void main(String[] args) throws Exception {
        try (ServerManager serverManager = new ServerManager()) {
            serverManager.init();
            serverManager.start();
        }
    }
}
