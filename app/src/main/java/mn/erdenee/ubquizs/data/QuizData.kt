package mn.erdenee.ubquizs.data

import mn.erdenee.ubquizs.model.Level
import mn.erdenee.ubquizs.model.Question

object QuizData {
    val levels = listOf(
        Level(
            levelNumber = 1,
            questions = listOf(
                Question(
                    id = 1,
                    text = "What is the capital of Mongolia?",
                    options = listOf("Ulaanbaatar", "Darkhan", "Erdenet", "Choibalsan"),
                    correctAnswerIndex = 0
                )
            )
        ),
        Level(
            levelNumber = 2,
            questions = listOf(
                Question(
                    id = 2,
                    text = "What is the primary language spoken in Mongolia?",
                    options = listOf("Russian", "Mandarin", "Mongolian", "Kazakh"),
                    correctAnswerIndex = 2
                )
            )
        ),
        Level(
            levelNumber = 3,
            questions = listOf(
                Question(
                    id = 3,
                    text = "Which desert covers the southern part of Mongolia?",
                    options = listOf("Sahara", "Gobi", "Kalahari", "Atacama"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Level(
            levelNumber = 4,
            questions = listOf(
                Question(
                    id = 4,
                    text = "Who founded the Mongol Empire in 1206?",
                    options = listOf("Kublai Khan", "Genghis Khan", "Attila the Hun", "Batu Khan"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Level(
            levelNumber = 5,
            questions = listOf(
                Question(
                    id = 5,
                    text = "What is the traditional dwelling of Mongolian nomads?",
                    options = listOf("Teepee", "Igloo", "Ger (Yurt)", "Cabin"),
                    correctAnswerIndex = 2
                )
            )
        ),
        Level(
            levelNumber = 6,
            questions = listOf(
                Question(
                    id = 6,
                    text = "Which animal is NOT one of the 'Five Snouts' of traditional Mongolian herding?",
                    options = listOf("Horse", "Camel", "Sheep", "Pig"),
                    correctAnswerIndex = 3
                )
            )
        ),
        Level(
            levelNumber = 7,
            questions = listOf(
                Question(
                    id = 7,
                    text = "What is the national festival of Mongolia celebrated in July?",
                    options = listOf("Tsagaan Sar", "Naadam", "Golden Eagle Festival", "Lantern Festival"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Level(
            levelNumber = 8,
            questions = listOf(
                Question(
                    id = 8,
                    text = "What is the currency of Mongolia?",
                    options = listOf("Tugrik", "Ruble", "Yuan", "Won"),
                    correctAnswerIndex = 0
                )
            )
        ),
        Level(
            levelNumber = 9,
            questions = listOf(
                Question(
                    id = 9,
                    text = "Which mountain range is located in western Mongolia?",
                    options = listOf("Himalayas", "Altai", "Andes", "Rockies"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Level(
            levelNumber = 10,
            questions = listOf(
                Question(
                    id = 10,
                    text = "What is a popular traditional Mongolian drink made from fermented mare's milk?",
                    options = listOf("Kumis (Airag)", "Vodka", "Kvass", "Kefir"),
                    correctAnswerIndex = 0
                )
            )
        )
    )
}
