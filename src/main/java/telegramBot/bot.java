package telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class bot extends TelegramLongPollingBot {
    private final String BOT_NAME=" ";
    private final String BOT_TOKEN=" ";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public bot() {
        super();
        //создаём вспомогательный класс для работы с сообщениями, не являющимися командами
//        this.nonCommand = new NonCommand();
//        //регистрируем команды
//        register(new StartCommand("start", "Старт"));
//        register(new PlusCommand("plus", "Сложение"));
//        register(new MinusCommand("minus", "Вычитание"));
//        register(new PlusMinusCommand("plusminus", "Сложение и вычитание"));
//        register(new HelpCommand("help","Помощь"));
//        register(new SettingsCommand("settings", "Мои настройки"));

    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }


    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        //@MyTest1275Bot
        String answer = "Дорогой "+userName + " вы написали: "+msg.getText();
        setAnswer(chatId, userName, answer);
    }
}
