package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private final List<String> functionality = Arrays.asList(
            "You can manage your tasks",
            "Provides connection with Trello Account",
            "Application allows sending tasks to Trello"
    );
    Context context = new Context();

    public String buildTrelloCardEmail(String build) {
        buildContext(build);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyCardEmail(String build) {
        buildContext(build);
        return templateEngine.process("mail/daily-card-mail", context);
    }

    public void buildContext(String message) {
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://codingwithr4k3.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_goal", adminConfig.getCompanyGoal());
        context.setVariable("app_name", adminConfig.getAppName());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
    }
}
