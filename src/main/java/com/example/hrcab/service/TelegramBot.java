package com.example.hrcab.service;

import com.example.hrcab.configs.BotConfig;
import com.example.hrcab.models.Vacancy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


//Телеграм бот
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    final String chanelId = "-1002093121215";

    public TelegramBot(BotConfig config){this.config = config;}

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
            long userId = update.getCallbackQuery().getFrom().getId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(userId));
            message.setText("http://localhost:8080/feedback/"+update.getCallbackQuery().getData());
            message.setParseMode(ParseMode.MARKDOWNV2);
            try {execute(message);}
            catch (TelegramApiException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void postMessage(Vacancy vacancy){
        String answer = vacancy.getName()+"\n\n"+vacancy.getDescription()+"\n\nТребования: "+vacancy.getRequirements();
        SendMessage message = new SendMessage();
        message.setChatId(chanelId);
        message.setText(answer);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Откликнутся");
        button.setCallbackData(String.valueOf(vacancy.getId()));
        rowInline.add(button);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        try {execute(message);}
        catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }
}
