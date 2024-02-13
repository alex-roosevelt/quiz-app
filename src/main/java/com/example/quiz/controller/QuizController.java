package com.example.quiz.controller;

import com.example.quiz.exceptions.ResourceNotFoundException;
import com.example.quiz.model.QuizQuestion;
import com.example.quiz.repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

  @Autowired
  private QuizQuestionRepository questionRepository;

  @PostMapping("/checkAnswer")
  public boolean checkAnswer(@RequestParam Long questionId, @RequestParam String userAnswer) {
    QuizQuestion question = questionRepository.findById(questionId)
        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

    // Получаем правильный ответ из объекта вопроса
    String correctAnswer = question.getCorrectAnswer();

    // Проверяем, совпадает ли ответ пользователя с правильным ответом

    return userAnswer.equals(correctAnswer);
  }
}