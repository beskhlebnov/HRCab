package com.example.hrcab.controllers;

import com.example.hrcab.models.Users;
import com.example.hrcab.models.Vacancy;
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

@Controller
public class MainController {

    TelegramBot bot;
    TelegramBotsApi telegramBotsApi;

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;

    @Autowired
    public MainController(VacancyRepository vacancyRepository, UserRepository userRepository, TelegramBot bot) throws TelegramApiException {
        this.vacancyRepository = vacancyRepository;
        this.userRepository = userRepository;
        this.bot = bot;
        this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        this.telegramBotsApi.registerBot(this.bot);
    }



    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Users user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        Vacancy vacancy = new Vacancy();
        model.addAttribute("vacancy", vacancy);
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        model.addAttribute("vacancies", vacancies);
        return "Vacancy";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute(value = "vacancy") Vacancy vacancy){
        vacancyRepository.save(vacancy);
        bot.postMessage(vacancy);
        return "redirect:/";
    }

    @GetMapping("/feedback/{id}")
    public String feedback(@PathVariable String id){
        Vacancy vacancy = vacancyRepository.findById(Long.parseLong(id)).get();
        System.out.println(vacancy.getName());
        return "redirect:/";
    }

}
