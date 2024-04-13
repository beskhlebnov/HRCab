package com.example.hrcab.controllers;

import com.example.hrcab.models.Feedback;
import com.example.hrcab.models.Users;
import com.example.hrcab.models.Vacancy;
import com.example.hrcab.repos.FeedbackRepository;
import com.example.hrcab.repos.UserRepository;
import com.example.hrcab.repos.VacancyRepository;
import com.example.hrcab.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//Главный контроллер

@Controller
public class MainController {

    //Телеграм бот
    TelegramBot bot;

    //Регистратор ботов телеграм
    TelegramBotsApi telegramBotsApi;

    //Репозитории для работы с БД
    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public MainController(VacancyRepository vacancyRepository, UserRepository userRepository,FeedbackRepository feedbackRepository, TelegramBot bot) throws TelegramApiException {
        this.feedbackRepository = feedbackRepository;
        this.vacancyRepository = vacancyRepository;
        this.userRepository = userRepository;
        this.bot = bot;
        this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        this.telegramBotsApi.registerBot(this.bot);
    }


    //Отображение страницы вакансий
    @GetMapping("/")
    public String getVacancy(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Users user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        Vacancy vacancy = new Vacancy();
        model.addAttribute("vacancy", vacancy);
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        for (Vacancy var: vacancies){
            var.setFeeds(feedbackRepository.findByVacancyId(var.getId()));
        }
        model.addAttribute("vacancies", vacancies);
        return "Vacancy";
    }

    //Добавление вакансии
    @PostMapping("/add")
    public String postVacancy(@ModelAttribute(value = "vacancy") Vacancy vacancy){
        vacancyRepository.save(vacancy);
        bot.postMessage(vacancy);
        return "redirect:/";
    }

    //Просмотр вакансии соискателем
    @GetMapping("/feedback/{id}")
    public String getFeedback(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id, Model model){
        Vacancy vacancy = vacancyRepository.findById(Long.parseLong(id)).get();
        Users user = userRepository.findByUsername(userDetails.getUsername());
        Feedback feedback = new Feedback();
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("feedback", feedback);
        model.addAttribute("user", user);
        System.out.println(vacancy.getName());
        return "Feedback";
    }

    //Оформление заявки по вакансии
    @PostMapping("/feedback")
    public String postFeedback(@ModelAttribute(value = "feedback") Feedback feedback){
        feedbackRepository.save(feedback);
        return "redirect:/feedback/my";

    }

    //Просмотр заявок пользователя
    @GetMapping("/feedback/my")
    public String getMyFeed(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Users user = userRepository.findByUsername(userDetails.getUsername());
        Iterable<Feedback> feedbacks = feedbackRepository.findByUsersId(user.getId());
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("user", user);
        return "MyFeed";
    }

}
