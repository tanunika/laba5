package server.Commands;


public interface Commands {
    String getName();
    String execute(String arguments);
    default boolean isInteractive() {
        return false; // по умолчанию — нет
    }
    // если команда интерактивная, сервер сообщает, что нужно ввести дальше
    default String nextPrompt(String previousInput) {
        return null; // по умолчанию — не используется
    }

    // если интерактивная команда готова, выполнит её
    default boolean isReady() {
        return true;
    }

    default void reset() {
        // сброс состояния команды (для многократного использования)
    }
}