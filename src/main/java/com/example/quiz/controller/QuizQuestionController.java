package com.example.quiz.controller;

import com.example.quiz.exceptions.ResourceNotFoundException;
import com.example.quiz.model.QuizQuestion;
import com.example.quiz.repository.QuizQuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuizQuestionController {
  @Autowired
  private QuizQuestionRepository questionRepository;

  @GetMapping
  public List<QuizQuestion> getAllQuestions() {
    return questionRepository.findAll();
  }

  @PostMapping
  public QuizQuestion addQuestion(@RequestParam String question, @RequestParam String answer) {

    if (isNullOrEmpty(question) || isNullOrEmpty(answer)) {
      throw new ResourceNotFoundException("Fields are empty");
    }

    QuizQuestion quizQuestion = new QuizQuestion();
    quizQuestion.setQuestion(question);
    quizQuestion.setCorrectAnswer(answer);

    return questionRepository.save(quizQuestion);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuizQuestion> getQuestionById(@PathVariable Long id) {
    QuizQuestion question = questionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    return ResponseEntity.ok(question);
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuizQuestion> updateQuestion(@PathVariable Long id, @RequestParam String question, @RequestParam String answer) {
    QuizQuestion questionEntity = questionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

    if (isNullOrEmpty(question) || isNullOrEmpty(answer)) {
      throw new ResourceNotFoundException("Fields are empty");
    }

    questionEntity.setQuestion(question);
    questionEntity.setCorrectAnswer(answer);

    QuizQuestion updatedQuestion = questionRepository.save(questionEntity);
    return ResponseEntity.ok(updatedQuestion);
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
    QuizQuestion question = questionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    questionRepository.delete(question);
    return ResponseEntity.ok().build();
  }
}
