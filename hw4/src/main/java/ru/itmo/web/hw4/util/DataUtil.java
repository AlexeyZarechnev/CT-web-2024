package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", 2, User.Color.RED),
            new User(6, "pashka", "Pavel Mavrin", 0, User.Color.GREEN),
            new User(9, "geranazavr555", "Georgiy Nazarov", 0, User.Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", 2, User.Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
        new Post(0, "Первые задачки", "🌟 Привет, Codeforces! Сегодня я решил попробовать свои силы в решении задач на динамическое программирование. Это всегда было для меня сложной темой, но я готов учиться! Какие советы вы можете дать новичкам? Делитесь своими методами и любимыми задачами! 💻✨ #Codeforces #DP\n", 1),
        new Post(1, "Первый раунд", "🚀 Ура! Я только что закончил свой первый контест на Codeforces и получил +20 рейтинга! 🎉 Это было сложно, но очень интересно. Спасибо всем участникам за классные задачи! Какие ваши самые запоминающиеся моменты с контестов? Делитесь в комментариях! #Codeforces #CompetitiveProgramming\n" , 1), 
        new Post(2, "Кружок по интересам", "Привет, друзья! 🌍 Недавно я начал активно участвовать в контестах на Codeforces и столкнулся с некоторыми трудностями. Особенно это касается задач на графы. Я заметил, что многие участники используют различные алгоритмы, такие как BFS и DFS, но иногда мне сложно понять, когда применять тот или иной метод. \n" + //
                        "\n" + //
                        "Я решил создать небольшую группу для обсуждения алгоритмов и стратегий решения задач. Если кто-то заинтересован, пишите в комментариях! Давайте вместе разберем сложные задачи и поможем друг другу стать лучше! 💪 #Codeforces #Algorithms\n", 11),
        new Post(3,"Подготовка к контестам", "Привет, сообщество Codeforces! 🌟 Хочу поделиться своим опытом участия в последнем контесте и тем, как я готовился к нему. Я заметил, что многие участники имеют свои собственные стратегии: кто-то решает задачи по порядку, а кто-то сначала смотрит на более легкие. \n" + //
                        "\n" + //
                        "Я выбрал смешанный подход: сначала просмотрел все задачи, выбрал те, которые кажутся наиболее интересными и по ним приступил к решению. Однако не обошлось без ошибок — несколько раз потратил много времени на задачи, которые оказались сложнее, чем казалось. \n" + //
                        "\n" + //
                        "В результате я понял, что важно не только уметь решать задачи, но и правильно распределять время. Как вы готовитесь к контестам? Делитесь своим опытом! 💻🔥 #Codeforces #CompetitiveProgramming", 11) 
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}

